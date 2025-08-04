package com.team1.hrbank.global.error;

import com.team1.hrbank.global.constant.ErrorCode;

public class ChangeLogException extends BusinessException {

    public ChangeLogException(ErrorCode errorCode) {
        super(errorCode);
    }
}
