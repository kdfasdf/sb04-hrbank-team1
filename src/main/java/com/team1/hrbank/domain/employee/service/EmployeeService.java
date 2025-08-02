package com.team1.hrbank.domain.employee.service;

import com.team1.hrbank.domain.changelog.service.ChangeLogService;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.repository.DepartmentRepository;
import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.dto.response.CursorPageResponseEmployeeDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.mapper.EmployeeMapper;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.file.service.FileMetadataService;
import com.team1.hrbank.domain.file.entity.FileMetadata;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final FileMetadataService fileMetadataService;
  private final EmployeeMapper employeeMapper;
  private final ChangeLogService changeLogService;

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto,
      MultipartFile profile, String clientIp) {

    validateDuplicateEmail(employeeCreateRequestDto.email());
    Department department = getValidateDepartment(employeeCreateRequestDto.departmentId());

    String employeeNumber = createEmployeeNumber();
    Employee employee = employeeMapper.toEmployee(employeeCreateRequestDto, employeeNumber,
        EmployeeStatus.ACTIVE, department);

    Employee savedEmployee = employeeRepository.save(employee);

    if (profile != null && !profile.isEmpty()) {
      FileMetadata fileMetadata = fileMetadataService.uploadProfileImage(savedEmployee.getId(),
          profile);
      savedEmployee.setFileMetadata(fileMetadata);
    }

    changeLogService.recordCreateLog(savedEmployee, employeeCreateRequestDto.memo(), clientIp);

    return employeeMapper.toEmployeeDto(savedEmployee);
  }

  @Transactional
  public EmployeeDto updateEmployee(Long employeeId,
      EmployeeUpdateRequestDto employeeUpdateRequestDto,
      MultipartFile profile,
      String clientIp) {
    validateDuplicateEmail(employeeUpdateRequestDto.email());
    Department newDepartment = getValidateDepartment(employeeUpdateRequestDto.departmentId());
    Employee findEmployee = getValidateEmployee(employeeId);
    FileMetadata fileMetadata = findEmployee.getFileMetadata();
    Department department = findEmployee.getDepartment();

    Department oldDepartment = new Department(department.getName(),
        department.getDescription(),
        department.getEstablishedDate(), department.getEmployees());

    FileMetadata oldFileMetadata = new FileMetadata(fileMetadata.getOriginalName(),
        fileMetadata.getSavedName(), fileMetadata.getFilePath(), fileMetadata.getFileType(),
        fileMetadata.getFileSize(), fileMetadata.getFilePath());

    Employee oldEmployee = new Employee(
        findEmployee.getEmployeeNumber(), findEmployee.getName(), findEmployee.getEmail(),
        findEmployee.getPosition(), findEmployee.getHireDate(), findEmployee.getStatus(),
        oldDepartment, oldFileMetadata);

    findEmployee.setName(employeeUpdateRequestDto.name());
    findEmployee.setEmail(employeeUpdateRequestDto.email());
    findEmployee.setPosition(employeeUpdateRequestDto.position());
    findEmployee.setHireDate(employeeUpdateRequestDto.hireDate());
    findEmployee.setStatus(EmployeeStatus.valueOf(employeeUpdateRequestDto.status()));

    FileMetadata newFileMetadata = fileMetadataService.uploadProfileImage(findEmployee.getId(),
        profile);

    findEmployee.setDepartment(newDepartment);
    findEmployee.setFileMetadata(newFileMetadata);

    changeLogService.recordUpdateLog(oldEmployee, findEmployee, employeeUpdateRequestDto.memo(),
        clientIp);

    return employeeMapper.toEmployeeDto(employeeRepository.save(findEmployee));
  }

  @Transactional
  public void deleteEmployee(Long employeeId, String clientIp) {
    Employee employee = getValidateEmployee(employeeId);
    changeLogService.recordDeleteLog(employee, "직원 삭제", clientIp);
    // 프로토 타입에서 확인 결과 직원 삭제는 따로 메모는 없고 일괄적으로 "직원 삭제" 란 메시지가 자동으로 들어갑니다.
    employeeRepository.delete(employee);
  }

  public CursorPageResponseEmployeeDto findEmployees(CursorPageRequestDto cursorPageRequestDto) {

    List<Employee> employees = employeeRepository.findAllEmployeesByRequestNative(
        cursorPageRequestDto);
    Long totalElements = (long) employees.size();

    int startIndex = 0;  // 다음 커서를 찾으면
    Long idAfter = cursorPageRequestDto.idAfter(); // 이게 Long 타입이라고 가정

    if (idAfter != null) {
      for (int i = 0; i < employees.size(); i++) {
        if (employees.get(i).getId().equals(idAfter)) {
          startIndex = i;
          break;
        }
      }
    }

    int endIndex = (employees.size() - startIndex) % cursorPageRequestDto.size() == 0
        ? cursorPageRequestDto.size() + startIndex
        : (employees.size() - startIndex) % cursorPageRequestDto.size() + startIndex;

    List<Employee> pagedEmployees = employees.subList(startIndex, endIndex);
    List<EmployeeDto> content = pagedEmployees.stream()
        .map(employeeMapper::toEmployeeDto)
        .toList();

    String nextCursor = null;
    Long nextIdAfter = null;
    Integer size = endIndex - startIndex;
    Boolean hasNext = null;

    if (endIndex + 1 < totalElements) {

      switch (cursorPageRequestDto.sortField()) {
        case "name":
          nextCursor = employees.get(endIndex + 1).getName();
          break;
        case "employeeNumber":
          nextCursor = employees.get(endIndex + 1).getEmployeeNumber();
          break;
        case "hireDate":
          nextCursor = String.valueOf(employees.get(endIndex + 1).getHireDate());
          break;
      }

      nextIdAfter = employees.get(endIndex + 1).getId();
      hasNext = true;
    }

    return new CursorPageResponseEmployeeDto(content, nextCursor, nextIdAfter, size, totalElements,
        hasNext);
  }

  public EmployeeDto findEmployee(Long employeeId) {
    return employeeMapper.toEmployeeDto(employeeRepository.findById(employeeId).get());
  }

  private String createEmployeeNumber() {
    String prefix = "EMP"; // Employee 의 약자
    String year = String.valueOf(LocalDate.now().getYear()); // 연도

    String timestamp = String.valueOf(System.currentTimeMillis());  // 13자리
    int randomTwoDigits = (int) (Math.random() * 90) + 10;         // 10~99 사이 두자리 난수
    String unique = randomTwoDigits + timestamp; // 랜덤한 15자리 숫자

    String employeeNumber = prefix + "-" + year + "-" + unique;

    // 중복 확인 후 재귀 호출
    if (employeeRepository.findByEmployeeNumber(employeeNumber).isPresent()) {
      return createEmployeeNumber(); // 중복 시 다시 생성
    }

    return employeeNumber;
  }

  private Department getValidateDepartment(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
  }

  private void validateDuplicateEmail(String email) {
    if (employeeRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }
  }

  private Employee getValidateEmployee(long employeeId) {
    return employeeRepository.findById(employeeId)
        .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
  }

}