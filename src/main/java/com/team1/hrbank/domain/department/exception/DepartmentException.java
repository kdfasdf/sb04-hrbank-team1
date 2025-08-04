package com.team1.hrbank.domain.department.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import com.team1.hrbank.global.error.BusinessException;

public class DepartmentException extends BusinessException {

  public DepartmentException(ErrorCode errorCode) {
    super(errorCode);
  }
}
