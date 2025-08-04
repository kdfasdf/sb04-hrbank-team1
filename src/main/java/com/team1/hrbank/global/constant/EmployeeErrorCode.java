package com.team1.hrbank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmployeeErrorCode implements ErrorCode {

  EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "찾을 수 없습니다.", "해당 직원을 찾을 수 없습니다."),
  EMPLOYEE_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST.value(), "중복된 이메일 입니다.", "해당 이메일은 중복된 이메일입니다.");

  private final int status;
  private final String message;
  private final String details;
}
