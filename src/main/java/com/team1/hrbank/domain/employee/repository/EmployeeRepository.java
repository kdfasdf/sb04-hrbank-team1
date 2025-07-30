package com.team1.hrbank.domain.employee.repository;

import com.team1.hrbank.domain.employee.entity.Employee;
import java.util.Optional;

public interface EmployeeRepository {

  Employee createEmployee(Employee employee);

  Employee updateEmployee(Employee employee);

  void deleteEmployee(Employee employee);

  Optional<Employee> findEmployeeByEmployeeId(long id);

  Optional<Employee> findEmployeeByEmployeeNumber(String employeeNumber);

  Optional<Employee> findEmployeeByEmail(String email);

  long countByDepartmentId(Long departmentId);
}