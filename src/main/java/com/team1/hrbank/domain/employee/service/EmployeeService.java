package com.team1.hrbank.domain.employee.service;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.request.EmployeeCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {

  EmployeeDto createEmployee(EmployeeCreateRequest employeeCreateRequest,
      MultipartFile profile);

}
