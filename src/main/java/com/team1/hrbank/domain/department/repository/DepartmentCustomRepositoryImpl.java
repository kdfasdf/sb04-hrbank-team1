package com.team1.hrbank.domain.department.repository;

import com.team1.hrbank.domain.department.dto.request.DepartmentSearchRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentCustomRepositoryImpl implements DepartmentCustomRepository {

  @PersistenceContext
  private EntityManager em;

  public List<DepartmentDto> searchDepartments(DepartmentSearchRequestDto request) {

    String sortField = switch (request.sortField()) {
      case "name" -> "name";
      case "establishedDate" -> "established_date";
      default -> "established_date";
    };

    String sortDirection = "ASC".equalsIgnoreCase(request.sortDirection()) ? "ASC" : "DESC";

    String baseQuery = """
            SELECT
                d.id, d.name, d.description, d.established_date,
                (SELECT COUNT(*) FROM employee e WHERE e.department_id = d.id) AS employee_count
            FROM departments d
            WHERE
                (? IS NULL OR LOWER(d.name) LIKE '%' || LOWER(?) || '%' OR LOWER(d.description) LIKE '%' || LOWER(?) || '%')
                AND (? IS NULL OR d.id > ?)
            ORDER BY %s %s
            LIMIT ?
        """;

    String finalQuery = String.format(baseQuery, sortField, sortDirection);

    Query query = em.createNativeQuery(finalQuery, "DepartmentDtoMapping");
    query.setParameter(1, request.nameOrDescription());
    query.setParameter(2, request.nameOrDescription());
    query.setParameter(3, request.nameOrDescription());
    query.setParameter(4, request.idAfter());
    query.setParameter(5, request.idAfter());
    query.setParameter(6, request.size() != null ? request.size() : 10);

    @SuppressWarnings("unchecked")
    List<DepartmentDto> result = query.getResultList();

    return result;
  }

  public long countDepartments(String keyword) {
    String countQuery = """
            SELECT COUNT(*)
            FROM departments d
            WHERE (? IS NULL OR LOWER(d.name) LIKE '%' || LOWER(?) || '%' OR LOWER(d.description) LIKE '%' || LOWER(?) || '%')
        """;

    Query query = em.createNativeQuery(countQuery);
    query.setParameter(1, keyword);
    query.setParameter(2, keyword);
    query.setParameter(3, keyword);

    return ((Number) query.getSingleResult()).longValue();
  }
}
