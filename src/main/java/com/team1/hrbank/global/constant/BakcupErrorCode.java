package com.team1.hrbank.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BakcupErrorCode implements ErrorCode {

  RECENT_BACKUP_NOT_EXIST(400, "잘못된 요청입니다.", "백업 상태가 존재하지 않음"),
  BACKUP_FAILED(500, "백업 실패", "백업 프로세스 진행 중 에러 발생");

  private final int status;
  private final String message;
  private final String details;


}
