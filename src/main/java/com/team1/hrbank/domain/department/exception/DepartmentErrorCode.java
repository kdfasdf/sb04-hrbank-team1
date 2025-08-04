package com.team1.hrbank.domain.department.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentErrorCode implements ErrorCode {

  DEPARTMENT_NOT_FOUND(404, "DEPARTMENT_001", "부서를 찾을 수 없습니다."),
  DEPARTMENT_NAME_DUPLICATE(409, "DEPARTMENT_002", "중복된 부서명 입니다."),
  DEPARTMENT_CANT_DELETE(400, "DEPARTMENT_003", "부서에 직원이 소속되어있어 삭제할 수 없습니다.");


  private final int status;
  private final String message;
  private final String details;

}
