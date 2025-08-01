package com.team1.hrbank.domain.employee.service;

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

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto,
      MultipartFile profile) {

    validateDuplicateEmail(employeeCreateRequestDto.email());
    Department department = getValidateDepartment(employeeCreateRequestDto.departmentId());

    FileMetadata fileMetadata = null;
    if (profile != null && !profile.isEmpty()) {
      fileMetadata = fileMetadataService.uploadProfileImage(profile);
    }

    String employeeNumber = createEmployeeNumber();
    Employee employee = employeeMapper.toEmployee(employeeCreateRequestDto, employeeNumber,
        EmployeeStatus.ACTIVE, department,
        fileMetadata);

    return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
  }

  @Transactional
  public EmployeeDto updateEmployee(Long employeeId,
      EmployeeUpdateRequestDto employeeUpdateRequestDto,
      MultipartFile profile) {
    validateDuplicateEmail(employeeUpdateRequestDto.email());
    Department department = getValidateDepartment(employeeUpdateRequestDto.departmentId());
    Employee employee = getValidateEmployee(employeeId);

    employee.setName(employeeUpdateRequestDto.name());
    employee.setEmail(employeeUpdateRequestDto.email());
    employee.setPosition(employeeUpdateRequestDto.position());
    employee.setHireDate(employeeUpdateRequestDto.hireDate());

    EmployeeStatus employeeStatus = EmployeeStatus.valueOf(employeeUpdateRequestDto.status());
    employee.setStatus(employeeStatus);

    employee.setDepartment(department);

    FileMetadata fileMetadata = null;
    if (profile != null && !profile.isEmpty()) {
      fileMetadata = fileMetadataService.uploadProfileImage(profile);
    }
    employee.setFileMetaData(fileMetadata);

    return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
  }

  @Transactional
  public void deleteEmployee(Long employeeId) {
    employeeRepository.deleteById(employeeId);
  }

  public CursorPageResponseEmployeeDto findEmployees(CursorPageRequestDto cursorPageRequestDto) {

    List<Employee> employees = employeeRepository.findAllEmployeesByRequest(cursorPageRequestDto);
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

  private String createEmployeeNumber() {
    String prefix = "EMP"; // 직원
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
    Optional<Employee> employee = employeeRepository.findById(employeeId);
    if (employee.isEmpty()) {
      throw new IllegalArgumentException("Employee not found");
    }
    return employee.get();
  }

}