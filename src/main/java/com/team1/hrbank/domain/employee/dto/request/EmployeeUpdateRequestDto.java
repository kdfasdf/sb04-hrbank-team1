package com.team1.hrbank.domain.employee.dto.request;

import java.time.LocalDate;

public record EmployeeUpdateRequestDto(
    String name,
    String email,
    Long departmentId,
    String position,
    LocalDate hireDate,
    String status,
    String memo
) {

}
