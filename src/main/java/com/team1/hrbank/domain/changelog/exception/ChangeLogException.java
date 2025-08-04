package com.team1.hrbank.domain.changelog.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import com.team1.hrbank.global.error.BusinessException;

public class ChangeLogException extends BusinessException {

    public ChangeLogException(ErrorCode errorCode) {
        super(errorCode);
    }
}
