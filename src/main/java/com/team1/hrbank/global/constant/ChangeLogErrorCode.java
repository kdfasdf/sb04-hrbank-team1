package com.team1.hrbank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChangeLogErrorCode implements ErrorCode{

    INVALID_SORT_KEY(400, "CHANGE_LOG_001", "유효하지 않은 정렬 키입니다."),
    EMPLOYEE_BEFORE_AFTER_REQUIRED(400, "CHANGE_LOG_002", "직원 변경 전/후 정보가 필요합니다."),
    EMPLOYEE_NUMBER_REQUIRED(400, "CHANGE_LOG_003", "직원 사번 정보가 누락되었습니다.");

    private final int status;
    private final String message;
    private final String details;

}
