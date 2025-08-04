package com.team1.hrbank.global.constant;

import java.time.LocalDateTime;

public interface ErrorCode {
  int getStatus();
  String getMessage();
  String getDetails();
}

