package com.team1.hrbank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DepartmentErrorCode implements ErrorCode {

  DEPARTMENT_ID_NOT_INPUT(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", "부서 코드는 필수입니다."),
  DEPARTMENT_NAME_DUPLICATE(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", "중복된 부서명 입니다."),
  DEPARTMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", "부서를 찾을 수 없습니다."),
  DEPARTMENT_CANT_DELETE(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", "부서에 직원이 소속되어있어 삭제할 수 없습니다.");


  private final int status;
  private final String message;
  private final String details;

}
