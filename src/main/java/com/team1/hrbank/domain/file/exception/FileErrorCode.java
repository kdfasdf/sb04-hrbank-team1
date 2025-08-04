package com.team1.hrbank.domain.file.exception;

import com.team1.hrbank.global.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FileErrorCode implements ErrorCode {

  FILE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "파일을 찾을 수 없음", "해당 경로에 파일 없음"),

  FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 삭제 실패", "파일 삭제 실패"),

  INVALID_FILE_NAME(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 파일명", "파일명에 확장자가 없거나 null"),

  UNSUPPORTED_EXTENSION(HttpStatus.BAD_REQUEST.value(), "지원하지 않는 확장자",
      "다음 확장자만 허용! 프로필: png, jpg, jpeg / 백업: csv, log"),

  DIRECTORY_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "디렉토리 생성 실패",
      "파일 저장 폴더 생성 실패"),

  FILE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 저장 실패", "파일 저장 중 오류 발생"),

  FILE_RENAME_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 이름 변경 실패",
      "임시 파일 -> 대상 파일로 이름 변경 중 오류 발생"),

  CSV_WRITE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "CSV 파일 저장 실패", "백업 파일 생성 증 오류 발생"),

  LOG_WRITE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "log 파일 저장 실패", "오류 로그 파일 저장 실패"),

  FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 업로드 실패", "파일 업로드 중 오류 발생"),

  FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일 다운로드 실패", "파일 다운로드 중 오류 발생");

  private final int status;
  private final String message;
  private final String details;

  FileErrorCode(int status, String message, String details) {
    this.status = status;
    this.message = message;
    this.details = details;
  }
}
