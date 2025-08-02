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
            (:keyword IS NULL OR LOWER(d.name) LIKE LOWER(:keywordPattern) OR LOWER(d.description) LIKE LOWER(:keywordPattern))
            AND (:idAfter IS NULL OR d.id > :idAfter)
        ORDER BY %s %s
        """;

    String finalQuery = String.format(baseQuery, sortField, sortDirection);

    Query query = em.createNativeQuery(finalQuery, "DepartmentDtoMapping");
    String keyword = request.nameOrDescription();
    query.setParameter("keyword", keyword);
    query.setParameter("keywordPattern", keyword != null ? "%" + keyword + "%" : null);
    query.setParameter("idAfter", request.idAfter());
    query.setMaxResults(request.size() != null ? request.size() : 10); // ANSI SQL에 LIMIT 없음

    @SuppressWarnings("unchecked")
    List<DepartmentDto> result = query.getResultList();
    return result;
  }

  public long countDepartments(String keyword) {
    String countQuery = """
        SELECT COUNT(*)
        FROM departments d
        WHERE (
            ?1 IS NULL
            OR LOWER(d.name) LIKE LOWER(?1)
            OR LOWER(d.description) LIKE LOWER(?1)
        )
        """;

    Query query = em.createNativeQuery(countQuery);

    String keywordPattern = (keyword != null && !keyword.isBlank())
        ? "%" + keyword + "%" : null;

    query.setParameter(1, keywordPattern);

    return ((Number) query.getSingleResult()).longValue();
  }
}
