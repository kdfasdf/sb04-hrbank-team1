package com.team1.hrbank.global.error;

import com.team1.hrbank.global.constant.ErrorCode;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private String message;
  private String details;
  private int status;
  private LocalDateTime timestamp;

  public ErrorResponse(ErrorCode code) {
    this.message = code.getMessage();
    this.status = code.getStatus();
    this.details = code.getDetails();
    this.timestamp = LocalDateTime.now();
  }

}
