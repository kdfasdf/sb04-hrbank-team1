package com.team1.hrbank.domain.employee.repository;

import com.team1.hrbank.domain.employee.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

  Employee createEmployee(Employee employee);

  Employee updateEmployee(Employee employee);

  void deleteEmployee(Employee employee);

  Employee findEmployee(long id);

  Optional<Employee> findEmployeeByEmployeeNumber(String employeeNumber);

  Optional<Employee> findEmployeeByEmail(String email);
}
