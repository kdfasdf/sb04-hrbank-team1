package com.team1.hrbank.global.error;

import com.team1.hrbank.global.constant.ErrorCode;

public class EmployeeException extends BusinessException {

  public EmployeeException(ErrorCode errorCode) {
    super(errorCode);
  }
}
