package com.team1.hrbank.domain.employee.repository;

import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Employee> findAllEmployeesByRequestNative(CursorPageRequestDto dto) {

    StringBuilder sql = new StringBuilder(
        "SELECT e.* FROM employees e LEFT JOIN departments d ON e.department_id = d.id WHERE 1=1 ");

    if (dto.nameOrEmail() != null && !dto.nameOrEmail().isBlank()) {
      sql.append("AND (LOWER(e.name) LIKE LOWER(CONCAT('%', :nameOrEmail, '%')) ")
          .append("OR LOWER(e.email) LIKE LOWER(CONCAT('%', :nameOrEmail, '%'))) ");
    }
    if (dto.position() != null && !dto.position().isBlank()) {
      sql.append("AND LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%')) ");
    }
    if (dto.status() != null && !dto.status().isBlank()) {
      sql.append("AND e.status = :status ");
    }
    if (dto.departmentName() != null && !dto.departmentName().isBlank()) {
      sql.append("AND LOWER(d.name) LIKE LOWER(CONCAT('%', :departmentName, '%')) ");
    }

    // 동적 정렬 필드 및 방향
    String orderField;
    switch (dto.sortField()) {
      case "hireDate":
        orderField = "e.hire_date";
        break;
      case "employeeNumber":
        orderField = "e.employee_number";
        break;
      case "name":
        orderField = "e.name";
        break;
      default:
        throw new IllegalArgumentException("Invalid sort field: " + dto.sortField());
    }
    String orderDirection = "ASC".equalsIgnoreCase(dto.sortDirection()) ? "ASC" : "DESC";

    sql.append("ORDER BY ").append(orderField).append(" ").append(orderDirection);

    Query query = em.createNativeQuery(sql.toString(), Employee.class);

    if (dto.nameOrEmail() != null && !dto.nameOrEmail().isBlank()) {
      query.setParameter("nameOrEmail", dto.nameOrEmail());
    }
    if (dto.position() != null && !dto.position().isBlank()) {
      query.setParameter("position", dto.position());
    }
    if (dto.status() != null && !dto.status().isBlank()) {
      query.setParameter("status", dto.status());
    }
    if (dto.departmentName() != null && !dto.departmentName().isBlank()) {
      query.setParameter("departmentName", dto.departmentName());
    }

    return query.getResultList();
  }
}
