package com.team1.hrbank.global.api;

import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentPageResponseDto;
import com.team1.hrbank.global.constant.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "부서 관리", description = "부서 관리 API")
public interface DepartmentApi {

  @Operation(summary = "부서 등록", description = "새로운 부서를 등록합니다.")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 중복된 이름",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 400,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """)))
  })
  ResponseEntity<DepartmentDto> create(
      @RequestBody DepartmentCreateRequestDto departmentCreateRequestDto);


  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 중복된 이름",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 400,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """))),
      @ApiResponse(responseCode = "404", description = "부서를 찾을 수 없음",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 404,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """)))
  })
  @Operation(summary = "부서 수정", description = "부서 정보를 수정합니다.")
  ResponseEntity<DepartmentDto> update(
      @PathVariable("id") @Parameter(description = "부서 ID") Long id,
      @RequestBody DepartmentUpdateRequestDto departmentUpdateRequestDto
  );

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
      @ApiResponse(responseCode = "404", description = "부서를 찾을 수 없음",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 404,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """)))
  })
  @Operation(summary = "부서 상세 조회", description = "부서 상세 정보를 조회합니다.")
  ResponseEntity<DepartmentDto> findOne(
      @PathVariable("id") @Parameter(description = "부서 ID") Long id);


  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(schema = @Schema(implementation = DepartmentPageResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 404,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """)))
  })
  @Operation(summary = "부서 목록 조회", description = "부서 목록을 조회합니다.")
  ResponseEntity<DepartmentPageResponseDto> findDepartments(
      @RequestParam(required = false) @Parameter(description = "부서 이름 또는 설명") String nameOrDescription,
      @RequestParam(required = false) @Parameter(description = "이전 페이지의 마지막 요소 ID") Long idAfter,
      @RequestParam(required = false) @Parameter(description = "커서(다음 페이지 시작점)") String cursor,
      @RequestParam(required = false, defaultValue = "10") @Parameter(description = "페이지 크기(기본값: 10)") Integer size,
      @RequestParam(required = false, defaultValue = "establishedDate") @Parameter(description = "정렬 필드(name 또는 establishedDate)") String sortField,
      @RequestParam(required = false, defaultValue = "asc") @Parameter(description = "정렬 방향 (asc 또는 desc, 기본값 : asc)") String sortDirection
  );

  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "삭제 성공",
          content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
      @ApiResponse(responseCode = "400", description = "부서를 찾을 수 없음",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 400,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """))),
      @ApiResponse(responseCode = "404", description = "잘못된 요청 입니다.",
          content = @Content(schema = @Schema(implementation = ErrorCode.class), examples = @ExampleObject(value = """
                {
                  "timestamp": "2025-08-04T12:00:00Z",
                  "status": 404,
                  "message": "잘못된 요청입니다",
                  "details": "부서 코드는 필수 입니다."
                }
            """)))
  })
  @Operation(summary = "부서 삭제", description = "부서를 삭제 합니다.")
  ResponseEntity<Void> delete(@PathVariable("id") @Parameter(description = "부서 ID") Long id
  );

}


