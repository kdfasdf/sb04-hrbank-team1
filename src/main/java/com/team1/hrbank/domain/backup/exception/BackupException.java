package com.team1.hrbank.domain.backup.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import com.team1.hrbank.global.error.BusinessException;

public class BackupException extends BusinessException {
  public BackupException (ErrorCode errorCode) {
    super(errorCode);
  }
}
