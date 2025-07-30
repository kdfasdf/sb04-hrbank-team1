package com.team1.hrbank.domain.employee.mapper;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.request.EmployeeCreateRequest;
import java.time.LocalDate;

public class EmployeeMapper {

  public static EmployeeDto toEmployeeDto(Employee employee) {
    Long id = employee.getId();
    String name = employee.getName();
    String email = employee.getEmail();
    String employeeNumber = employee.getEmployeeNumber();

    Long departmentId = employee.getDepartment().getId();
    String departmentName = employee.getDepartment().getName();

    String position = employee.getPosition();
    LocalDate hireDate = employee.getHireDate();
    EmployeeStatus status = employee.getStatus();

    Long profileImageId = employee.getFileMetaData().getId();

    return new EmployeeDto(id, name, email, employeeNumber, departmentId, departmentName, position,
        hireDate, status, profileImageId);
  }

  public static Employee toEmployee(EmployeeCreateRequest employeeCreateRequest,
      String employeeNumber, Department department, FileMetadata fileMetadata) {

    String name = employeeCreateRequest.name();
    String email = employeeCreateRequest.email();

    String position = employeeCreateRequest.position();
    LocalDate hireDate = employeeCreateRequest.hireDate();

    return new Employee(employeeNumber, name, email, position, hireDate,
        EmployeeStatus.ACTIVE, department, fileMetadata);
  }
}