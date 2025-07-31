package com.team1.hrbank.domain.employee.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.PathBuilder;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import jakarta.persistence.EntityManager;
import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;


public class QueryDslEmployeeRepositoryImpl implements QueryDslEmployeeRepository {

  private final JPAQueryFactory queryFactory;

  public QueryDslEmployeeRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Employee> findAllEmployeesByRequest(CursorPageRequestDto cursorPageRequestDto) {

    QEmployee employee = QEmployee.employee;

    String sortField = cursorPageRequestDto.sortField();
    String sortDirection = cursorPageRequestDto.sortDirection();

    String nameOrEmail = cursorPageRequestDto.nameOrEmail();       // nullable
    String departmentName = cursorPageRequestDto.departmentName(); // nullable
    String position = cursorPageRequestDto.position();             // nullable
    String status = cursorPageRequestDto.status();                 // nullable, String or Enum?

    if (nameOrEmail == null) {
      nameOrEmail = "";
    }
    if (departmentName == null) {
      departmentName = "";
    }
    if (position == null) {
      position = "";
    }
    if (status == null) {
      status = "";
    }

    Order order = Order.DESC; // 기본 DESC
    if ("ASC".equalsIgnoreCase(sortDirection)) {
      order = Order.ASC;
    }

    // 조건들 조합
    BooleanBuilder where = new BooleanBuilder();
    if (!nameOrEmail.isBlank()) {
      where.and(
          employee.name.containsIgnoreCase(nameOrEmail)
              .or(employee.email.containsIgnoreCase(nameOrEmail))
      );
    }
    if (!position.isBlank()) {
      where.and(employee.position.containsIgnoreCase(position));
    }
    if (!status.isBlank()) {
      where.and(employee.status.equalsIgnoreCase(status));
    }
    if (!departmentName.isBlank()) {
      where.and(employee.department.name.containsIgnoreCase(departmentName));
    }

    // 쿼리 실행
    List<Employee> employees = queryFactory
        .selectFrom(employee)
        .where(where)
        .orderBy(order)
        .fetch();

    // ./gradlew clean build 을 터미널에서 실행하고 빌드 실패가 안뜸, 지금 서로의 코드가 맞물려 작동하지 않기 때문에 빌드가 안되서 당장은 안됨

    return employees;
  }

}