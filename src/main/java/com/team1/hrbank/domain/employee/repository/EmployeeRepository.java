package com.team1.hrbank.domain.employee.repository;

import com.team1.hrbank.domain.employee.dto.response.EmployeeDistributionDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long>,
    EmployeeRepositoryCustom {

  Optional<Employee> findByEmployeeNumber(String employeeNumber);

  Optional<Employee> findByEmail(String email);

  long countByDepartmentId(Long departmentId);

  // JOIN FETCH 대체용
  @EntityGraph(attributePaths = {"department", "fileMetaData"})
  Optional<Employee> findById(Long id);

  // 대시보드
  @Query("""
          SELECT COUNT(e) FROM Employee e
          WHERE e.status = :status
      """)
  int countByStatus(@Param("status") EmployeeStatus status);

  @Query("""
      SELECT COUNT(e) FROM Employee e
      WHERE e.hireDate BETWEEN :fromDate AND :toDate
      """)
  long countByHireDate(
      @Param("fromDate") LocalDate fromDate,
      @Param("toDate") LocalDate toDate
  );

  @Query("""
      SELECT COUNT(e) FROM Employee e
      """)
  long countAll();

  List<Employee> findByStatus(EmployeeStatus status);
}
