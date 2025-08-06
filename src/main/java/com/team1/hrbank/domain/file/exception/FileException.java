package com.team1.hrbank.domain.file.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import com.team1.hrbank.global.error.BusinessException;

public class FileException extends BusinessException {

  public FileException(ErrorCode errorCode) {
    super(errorCode);
  }
}
