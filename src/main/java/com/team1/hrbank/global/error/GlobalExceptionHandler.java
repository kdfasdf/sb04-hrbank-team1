package com.team1.hrbank.global.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ErrorResponse handleRuntimeException(BusinessException e) {
    return new ErrorResponse(e.getErrorCode());
  }
}
