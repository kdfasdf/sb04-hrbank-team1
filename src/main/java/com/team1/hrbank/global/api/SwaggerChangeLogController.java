package com.team1.hrbank.global.api;

import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogDiffResponse;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "직원 정보 수정 이력 관리", description = "직원 정보 수정 이력 관리 API")
public interface SwaggerChangeLogController {

    @Operation(summary = "직원 정보 수정 이력 목록 조회", description = "직원 정보 수정 이력 목록을 조회합니다. 상세 변경 내용은 포함되지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ChangeLogSearchResponse.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 정렬 키",
                    content = @Content(examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 400,
                  "message": "잘못된 요청입니다",
                  "details": "유효하지 않은 정렬 키입니다."
                }
            """)))
    })
    ResponseEntity<ChangeLogSearchResponse> getChangeLogs(
            @Parameter(description = "검색 조건이 담긴 요청 객체") @ModelAttribute ChangeLogSearchRequest changeLogSearchRequest
    );

    @Operation(summary = "직원 정보 수정 이력 상세 조회", description = "지정된 이력 ID의 필드 변경 상세 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ChangeLogDiffResponse.class))),
            @ApiResponse(responseCode = "404", description = "이력을 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 404,
                  "message": "요청하신 이력이 존재하지 않습니다",
                  "details": "이력을 찾을 수 없습니다."
                }
            """)))
    })
    ResponseEntity<List<ChangeLogDiffResponse>> getDiffs(
            @Parameter(description = "체인지로그 ID") @PathVariable Long changeLogId
    );

    @Operation(summary = "직원 정보 수정 이력 건수 조회", description = "기간 내에 생성된 수정 이력 건수를 조회합니다. 파라미터가 없으면 최근 7일 기준으로 조회됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 날짜 범위",
                    content = @Content(examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 400,
                  "message": "잘못된 요청입니다",
                  "details": "유효하지 않은 날짜 범위입니다."
                }
            """)))
    })
    ResponseEntity<Long> getChangeLogsCount(
            @Parameter(description = "시작 일시", example = "2025-08-01T00:00:00")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,

            @Parameter(description = "종료 일시", example = "2025-08-04T23:59:59")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    );
}
