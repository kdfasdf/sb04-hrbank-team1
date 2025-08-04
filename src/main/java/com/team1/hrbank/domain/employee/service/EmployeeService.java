package com.team1.hrbank.domain.employee.service;

import com.team1.hrbank.domain.changelog.service.ChangeLogService;

import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.repository.DepartmentRepository;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.dto.response.CursorPageResponseEmployeeDto;
import com.team1.hrbank.domain.employee.dto.response.EmployeeDistributionDto;
import com.team1.hrbank.domain.employee.dto.response.EmployeeTrendDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.entity.TimeUnitType;
import com.team1.hrbank.domain.employee.mapper.EmployeeMapper;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.file.service.FileMetadataService;
import com.team1.hrbank.domain.file.entity.FileMetadata;

import com.team1.hrbank.global.constant.EmployeeErrorCode;
import com.team1.hrbank.domain.employee.exception.EmployeeException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    Department newDepartment = getValidateDepartment(employeeUpdateRequestDto.departmentId());
    Employee findEmployee = getValidateEmployee(employeeId);

    if (!employeeUpdateRequestDto.email().equals(findEmployee.getEmail())) {
      validateDuplicateEmail(
          employeeUpdateRequestDto.email()); // 수정 전과 후가 같은 이메일이 아닐 때, 그리고 다른 직원읭 이메일과 중복 될 때 예외 발생
    }

    FileMetadata fileMetadata = findEmployee.getFileMetadata();
    Department department = findEmployee.getDepartment();

    Department oldDepartment = new Department(department.getName(),
        department.getDescription(),
        department.getEstablishedDate(), department.getEmployees());

    FileMetadata oldFileMetadata = null;

    if (fileMetadata != null) {
      oldFileMetadata = new FileMetadata(fileMetadata.getOriginalName(),
          fileMetadata.getSavedName(), fileMetadata.getExtension(), fileMetadata.getFileType(),
          fileMetadata.getFileSize(), fileMetadata.getFilePath());
    }
    Employee oldEmployee = new Employee(
        findEmployee.getEmployeeNumber(), findEmployee.getName(), findEmployee.getEmail(),
        findEmployee.getPosition(), findEmployee.getHireDate(), findEmployee.getStatus(),
        oldDepartment, oldFileMetadata);

    findEmployee.setName(employeeUpdateRequestDto.name());
    findEmployee.setEmail(employeeUpdateRequestDto.email());
    findEmployee.setPosition(employeeUpdateRequestDto.position());
    findEmployee.setHireDate(employeeUpdateRequestDto.hireDate());
    findEmployee.setStatus(EmployeeStatus.valueOf(employeeUpdateRequestDto.status()));

    FileMetadata newFileMetadata = null;
    if (profile != null) {
      newFileMetadata = fileMetadataService.uploadProfileImage(findEmployee.getId(),
          profile);
    } else if (oldFileMetadata != null) {
      newFileMetadata = oldFileMetadata;
    }

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
    if (totalElements == 0) {
      return new CursorPageResponseEmployeeDto(List.of(), null, null, 0, totalElements,
          false);
    }

    int startIndex = 0;  // 다음 커서를 찾으면
    Long idAfter = cursorPageRequestDto.idAfter(); // 이게 Long 타입이라고 가정

    if (idAfter != null) {
      for (int i = 0; i < employees.size(); i++) {
        if (employees.get(i).getId().equals(idAfter)) {
          startIndex = i - 1;
          break;
        }
      }
    }

    int endIndex = 0;
    if (idAfter == null) {
      // 최대 30, 적어도 employees의 크기
      endIndex = Math.min(employees.size(), cursorPageRequestDto.size());
    } else {
      endIndex =
          Math.min(employees.size() - startIndex, cursorPageRequestDto.size()) + startIndex;
    }

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
      return createEmployeeNumber(); // 중복 시 다시 생성 1밀리초당 90개의 데이터를 생성하지 않는 이상 이론상 호출되지 않음
    }

    return employeeNumber;
  }

  // 대시보드
  public long countEmployees(EmployeeStatus status, LocalDate fromDate, LocalDate toDate) {
    if (fromDate != null && toDate == null) {
      toDate = LocalDate.now();
    }

    if (status != null && fromDate == null && toDate == null) {
      return employeeRepository.countByStatus(status);
    }

    if (status == null && fromDate != null) {
      return employeeRepository.countByHireDate(fromDate, toDate);
    }

    return employeeRepository.countAll();
  }

  public List<EmployeeTrendDto> getEmployeeTrend(LocalDate from, LocalDate to, TimeUnitType unit) {
    if (to == null) {
      to = LocalDate.now();
    }

    if (from == null) {
      from = getDefaultFrom(to, unit, 12);
    }

    List<EmployeeTrendDto> result = new ArrayList<>();
    List<LocalDate> periods = calculatePeriods(from, to, unit);

    Long prevCount = null;

    for (int i = 0; i < periods.size() - 1; i++) {
      LocalDate start = periods.get(i);
      LocalDate end = periods.get(i + 1).minusDays(1);

      long count = employeeRepository.countByHireDate(start, end);
      long change = prevCount == null ? 0 : count - prevCount;
      double changeRate =
          prevCount == null || prevCount == 0 ? 0.0 : change / (double) prevCount * 100.0;

      result.add(new EmployeeTrendDto(start, count, change, Math.round(changeRate * 10.0) / 10.0));
      prevCount = count;
    }

    return result;
  }

  public List<EmployeeDistributionDto> getEmployeeDistribution(String groupBy,
      EmployeeStatus status) {
    if (groupBy == null || groupBy.isBlank()) {
      groupBy = "department";
    }

    if (status == null) {
      status = EmployeeStatus.ACTIVE;
    }

    List<Employee> employees = employeeRepository.findByStatus(status);

    String finalGroupBy = groupBy;
    Map<String, Long> groupedCount = employees.stream()
        .collect(Collectors.groupingBy(
            e -> finalGroupBy.equalsIgnoreCase("position") ? e.getPosition()
                : e.getDepartment().getName(),
            Collectors.counting()
        ));

    long totalCount = groupedCount.values().stream().mapToLong(Long::longValue).sum();

    return groupedCount.entrySet().stream()
        .map(e -> new EmployeeDistributionDto(
            e.getKey(),
            e.getValue(),
            (double) e.getValue() / totalCount * 100
        ))
        .sorted(Comparator.comparingLong(EmployeeDistributionDto::count).reversed())
        .toList();
  }

  // 공통 메서드
  private Department getValidateDepartment(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
  }

  private void validateDuplicateEmail(String email) {
    if (employeeRepository.findByEmail(email).isPresent()) {
      throw new EmployeeException(EmployeeErrorCode.EMPLOYEE_EMAIL_DUPLICATE);
    }
  }

  private Employee getValidateEmployee(long employeeId) {
    return employeeRepository.findById(employeeId)
        .orElseThrow(() -> new EmployeeException(EmployeeErrorCode.EMPLOYEE_NOT_FOUND));
  }

  // 대시보드 공통 메서드
  private LocalDate getDefaultFrom(LocalDate base, TimeUnitType unit, int amount) {
    return switch (unit) {
      case DAY -> base.minusDays(amount);
      case WEEK -> base.minusWeeks(amount);
      case MONTH -> base.minusMonths(amount);
      case QUARTER -> base.minusMonths(amount * 3);
      case YEAR -> base.minusYears(amount);
    };
  }

  private List<LocalDate> calculatePeriods(LocalDate from, LocalDate to, TimeUnitType unit) {
    List<LocalDate> points = new ArrayList<>();
    LocalDate cursor = truncateToUnit(from, unit);
    LocalDate limit = to.plusDays(1); // 포함되도록

    while (cursor.isBefore(limit)) {
      points.add(cursor);
      cursor = incrementByUnit(cursor, unit);
    }

    return points;
  }

  private LocalDate truncateToUnit(LocalDate date, TimeUnitType unit) {
    return switch (unit) {
      case DAY -> date;
      case WEEK -> date.with(ChronoField.DAY_OF_WEEK, 1); // 월요일
      case MONTH -> date.withDayOfMonth(1);
      case QUARTER -> date.withMonth(((date.getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
      case YEAR -> date.withDayOfYear(1);
    };
  }

  private LocalDate incrementByUnit(LocalDate date, TimeUnitType unit) {
    return switch (unit) {
      case DAY -> date.plusDays(1);
      case WEEK -> date.plusWeeks(1);
      case MONTH -> date.plusMonths(1);
      case QUARTER -> date.plusMonths(3);
      case YEAR -> date.plusYears(1);
    };
  }

}