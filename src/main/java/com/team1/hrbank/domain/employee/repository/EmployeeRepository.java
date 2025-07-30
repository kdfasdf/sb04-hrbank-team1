package com.team1.hrbank.domain.employee.repository;

import com.team1.hrbank.domain.employee.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmployeeNumber(String employeeNumber);

  Optional<Employee> findByEmail(String email);

  long countByDepartmentId(Long departmentId);

  // JOIN FETCH 대체용
  @EntityGraph(attributePaths = {"department", "fileMetaData"})
  Optional<Employee> findById(Long id);
}
