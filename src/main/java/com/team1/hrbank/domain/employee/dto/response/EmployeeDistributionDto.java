package com.team1.hrbank.domain.employee.dto.response;

public record EmployeeDistributionDto(
    String groupKey, // 분류명(부서명 또는 직무명)
    long count,
    double percentage
) {

  public EmployeeDistributionDto {
    percentage = Math.round(percentage * 100) / 100.0;
  }
}
