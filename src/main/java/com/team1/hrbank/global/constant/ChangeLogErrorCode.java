package com.team1.hrbank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChangeLogErrorCode implements ErrorCode{

    INVALID_SORT_KEY(400, "잘못된 요청입니다", "유효하지 않은 정렬 키입니다."),
    EMPLOYEE_BEFORE_AFTER_REQUIRED(400, "잘못된 요청입니다", "직원 변경 전/후 정보가 필요합니다."),
    EMPLOYEE_NUMBER_REQUIRED(400, "잘못된 요청입니다", "직원 사번 정보가 누락되었습니다."),
    CHANGE_LOG_NOT_FOUND(404, "요청하신 이력이 존재하지 않습니다", "이력을 찾을 수 없습니다."),
    INVALID_DATE_RANGE(400, "잘못된 요청입니다", "유효하지 않은 날짜 범위입니다.");

    private final int status;
    private final String message;
    private final String details;

}
