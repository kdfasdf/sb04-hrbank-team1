package com.team1.hrbank.domain.employee.dto.request;

import java.time.LocalDate;

public record EmployeeCreateRequestDto(
    String name,
    String email,
    Long departmentId,
    String position,
    LocalDate hireDate,
    String memo
) {

}