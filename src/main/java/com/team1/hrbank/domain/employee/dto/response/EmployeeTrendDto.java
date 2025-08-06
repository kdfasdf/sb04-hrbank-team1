package com.team1.hrbank.domain.employee.dto.response;

import java.time.LocalDate;

public record EmployeeTrendDto(
    LocalDate date,
    long count,
    long change,
    double changeRate
) {

}
