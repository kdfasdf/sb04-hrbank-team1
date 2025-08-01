package com.team1.hrbank.domain.employee.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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

    String sortDirection = cursorPageRequestDto.sortDirection();
    String sortField = cursorPageRequestDto.sortField();
    String nameOrEmail = cursorPageRequestDto.nameOrEmail();
    String departmentName = cursorPageRequestDto.departmentName();
    String position = cursorPageRequestDto.position();
    String status = cursorPageRequestDto.status();

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

    // 필터 조건 조립
    BooleanBuilder where = new BooleanBuilder();
    if (!nameOrEmail.isBlank()) {
      where.and(employee.name.containsIgnoreCase(nameOrEmail)
          .or(employee.email.containsIgnoreCase(nameOrEmail)));
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

    Order order = "ASC".equalsIgnoreCase(sortDirection) ? Order.ASC : Order.DESC;
    Expression<?> orderPath;
    switch (sortField) {
      case "hireDate":
        orderPath = employee.hireDate;
        break;
      case "employeeNumber":
        orderPath = employee.employeeNumber;
        break;
      case "name":
        orderPath = employee.name;
        break;
      default:
        throw new IllegalArgumentException("Invalid sort field: " + sortField);
    }
    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(order, orderPath);

    return queryFactory
        .selectFrom(employee)
        .where(where)
        .orderBy(orderSpecifier)
        .fetch();
  }

  // Qentity를 사용하기 위해선 선행적으로 터미널에서 ./gradlew clean build 실행해야 함
  // 지금 서로의 코드가 맞물려 작동하지 않기 때문에 빌드가 안되서 당장은 안됨
}