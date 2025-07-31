package com.team1.hrbank.domain.employee.service;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.mapper.EmployeeMapper;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.employee.request.EmployeeCreateRequest;
import com.team1.hrbank.domain.employee.request.EmployeeUpdateRequest;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartMentRepository departMentRepository;
  private final FileMetaDataService fileMetaDataService;
  private final EmployeeMapper employeeMapper;

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateRequest employeeCreateRequest,
      MultipartFile profile) {

    validateEmail(employeeCreateRequest.email());
    Deparment deparment = validateDepartment(employeeCreateRequest.departmentId());

    FileMetaData fileMetaData = null;
    if (profile != null && !profile.isEmpty()) {
      fileMetaData = fileMetaDataService.createFileMetaData(profile);
    }

    String employeeNumber = createEmployeeNumber();
    Employee employee = employeeMapper.toEmployee(employeeCreateRequest, employeeNumber,
        EmployeeStatus.ACTIVE, deparment,
        fileMetaData);

    // TODO employeeCreateRequest.memo() 를 사용해서 수정 로그 남기는것 추가 필요함!!

    return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
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

  private Department validateDepartment(Long departmentId) {
    Optional<Department> department = departMentRepository.findDepartmentByDepartmentId(
        departmentId);
    if (department.isEmpty()) {
      throw new IllegalArgumentException("Department not found");
    }
    return department.get();
  }

  private void validateEmail(String email) {
    if (employeeRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Employee number already in use");
    }
  }
}