package com.team1.hrbank.domain.employee.service;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.mapper.EmployeeMapper;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.employee.request.EmployeeUpdateRequest;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;
  private final FileMetaDataService fileMetaDataService;
  private final EmployeeMapper employeeMapper;

  @Transactional
  public EmployeeDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto,
      MultipartFile profile) {

    validateDuplicateEmail(employeeCreateRequestDto.email());
    Deparment deparment = getValidateDepartment(employeeCreateRequestDto.departmentId());

    FileMetaData fileMetaData = null;
    if (profile != null && !profile.isEmpty()) {
      fileMetaData = fileMetaDataService.createFileMetaData(profile);
    }

    String employeeNumber = createEmployeeNumber();
    Employee employee = employeeMapper.toEmployee(employeeCreateRequestDto, employeeNumber,
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

  private Department getValidateDepartment(Long departmentId) {
    return departmentRepository.findById(departmentId)
        .orElseThrow(() -> new IllegalArgumentException("Department not found"));
  }

  private void validateDuplicateEmail(String email) {
    if (employeeRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }
  }
}