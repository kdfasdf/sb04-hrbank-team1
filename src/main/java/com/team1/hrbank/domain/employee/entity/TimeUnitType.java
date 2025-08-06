package com.team1.hrbank.domain.employee.entity;

public enum TimeUnitType {
  DAY,
  WEEK,
  MONTH,
  QUARTER,
  YEAR;

  public static TimeUnitType fromString(String value) {
    try {
      return valueOf(value.toUpperCase());
    } catch (Exception e) {
      return MONTH;
    }
  }
}
