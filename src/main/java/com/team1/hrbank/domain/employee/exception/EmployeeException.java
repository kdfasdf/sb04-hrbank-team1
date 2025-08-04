package com.team1.hrbank.domain.employee.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import com.team1.hrbank.global.error.BusinessException;

public class EmployeeException extends BusinessException {

  public EmployeeException(ErrorCode errorCode) {
    super(errorCode);
  }
}
