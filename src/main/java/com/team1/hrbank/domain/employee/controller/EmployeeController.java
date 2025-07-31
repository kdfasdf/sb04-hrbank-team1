package com.team1.hrbank.domain.employee.controller;


import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.request.EmployeeCreateRequest;
import com.team1.hrbank.domain.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "Employee", description = "직원 관리 API")
@RequestMapping("/api/employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<EmployeeDto> createEmployee(
      @RequestPart("employee") EmployeeCreateRequest employeeCreateRequest,
      @RequestPart(value = "profile", required = false) MultipartFile profile) {

    EmployeeDto employeeDto = employeeService.createEmployee(employeeCreateRequest, profile);
    return ResponseEntity.ok(employeeDto);
  }
}