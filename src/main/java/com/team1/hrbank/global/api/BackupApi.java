package com.team1.hrbank.global.api;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.dto.response.CursorPageResponseBackupDto;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "데이터 백업 관리", description = "백업 생성 및 조회 API")
public interface BackupApi {

  @Operation(
      summary = "데이터 백업 생성",
      description = "새로운 데이터 백업을 생성합니다"
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201",
              description = "백업 생성 성공",
              content = @Content(schema = @Schema(implementation = BackupDto.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
          @ApiResponse(
              responseCode = "409",
              description = "이미 진행중인 백업이 있음",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
          @ApiResponse(
              responseCode = "500",
              description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          )

      })
  ResponseEntity<BackupDto> createBackup(HttpServletRequest request);


  @Operation(
      summary = "데이터 백업 목록 조회",
      description = "필터, 정렬, 커서 기반 페이지 네이션으로 백업 목록 조회"
  )
  @Parameters({
      @Parameter(name = "worker", in = ParameterIn.QUERY, description = "작업자 IP"),
      @Parameter(name = "status", in = ParameterIn.QUERY, description = "백업 상태", schema = @Schema(implementation = BackupStatus.class)),
      @Parameter(name = "startedAtFrom", in = ParameterIn.QUERY, description = "시작 시간(부터)", schema = @Schema(type = "string", format = "date-time")),
      @Parameter(name = "startedAtTo", in = ParameterIn.QUERY, description = "시작 시간(까지)", schema = @Schema(type = "string", format = "date-time")),
      @Parameter(name = "idAfter", in = ParameterIn.QUERY, description = "이전 페이지 마지막 요소 ID"),
      @Parameter(name = "cursor", in = ParameterIn.QUERY, description = "커서(이전 페이지의 마지막 startedAt 값)"),
      @Parameter(name = "size", in = ParameterIn.QUERY, description = "페이지 크기", schema = @Schema(defaultValue = "10", type = "integer", format = "int32")),
      @Parameter(name = "sortField", in = ParameterIn.QUERY, description = "정렬 필드", schema = @Schema(defaultValue = "startedAt", allowableValues = {
          "startedAt", "endedAt", "status"})),
      @Parameter(name = "sortDirection", in = ParameterIn.QUERY, description = "정렬 방향", schema = @Schema(defaultValue = "DESC", allowableValues = {
          "ASC", "DESC"}))
  })
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "조회 성공",
          content = @Content(schema = @Schema(implementation = CursorPageResponseBackupDto.class))
      ),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 지원하지 않는 정렬 필드"),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  ResponseEntity<CursorPageResponseBackupDto> findAll(
      String worker,
      BackupStatus status,
      LocalDateTime startedAtFrom,
      LocalDateTime startedAtTo,
      Long idAfter,
      String cursor,  //zondatatime
      Integer size,
      String sortField,
      String sortDirection
  );


  @Operation(
      summary = "최근 백업 정보 조회",
      description = "지정된 상태의 가장 최근 백업을 반환합니다. 상태 미지정 시 COMPLETED 백업이 기본값으로 사용됩니다."
  )
  @Parameter(
      name = "status",
      in = ParameterIn.QUERY,
      description = "백업 상태",
      schema = @Schema(defaultValue = "COMPLETED", allowableValues = {"COMPLETED", "FAILED",
          "IN_PROGRESS", "SKIPPED"})
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "조회 성공",
          content = @Content(schema = @Schema(implementation = BackupDto.class))
      ),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 유효하지 않은 상태 값"),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  ResponseEntity<BackupDto> getLatest(
      @RequestParam(required = false, defaultValue = "COMPLETED") String status
  );
}
