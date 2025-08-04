package com.team1.hrbank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeErrorCode implements ErrorCode {

  EMPLOYEE_NOT_FOUND(404, "EMPLOYEE_001", "직원을 찾을 수 없습니다."),
  EMPLOYEE_EMAIL_DUPLICATE(409, "EMPLOYEE_002", "중복된 이메일입니다.");

  private final int status;
  private final String message;
  private final String details;
}
