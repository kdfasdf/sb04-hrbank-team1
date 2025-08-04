package com.team1.hrbank.domain.employee.controller;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.CursorPageRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.dto.response.CursorPageResponseEmployeeDto;
import com.team1.hrbank.domain.employee.dto.response.EmployeeDistributionDto;
import com.team1.hrbank.domain.employee.dto.response.EmployeeTrendDto;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.entity.TimeUnitType;
import com.team1.hrbank.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/employees")
@Tag(name = "직원 관리", description = "직원 관리 API")
public interface EmployeeApi {

  @Operation(
      summary = "직원 등록",
      description = "새로운 직원을 등록합니다.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "등록 성공",
              content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 또는 중복된 이메일",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "부서를 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping
  ResponseEntity<EmployeeDto> createEmployee(
      @RequestPart("employee") EmployeeCreateRequestDto employeeCreateRequestDto,
      @RequestPart(value = "profile", required = false) MultipartFile profile,
      HttpServletRequest request);

  @Operation(
      summary = "직원 수정",
      description = "직원 정보를 수정합니다.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공",
              content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 또는 중복된 이메일",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "직원 또는 부서를 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  ResponseEntity<EmployeeDto> updateEmployee(
      @Parameter(description = "직원 ID") @PathVariable() Long id,
      @RequestPart("employee") EmployeeUpdateRequestDto employeeUpdateRequestDto,
      @RequestPart(value = "profile", required = false) MultipartFile profile,
      HttpServletRequest request);

  @Operation(
      summary = "직원 삭제",
      description = "직원을 삭제합니다.",
      responses = {
          @ApiResponse(responseCode = "204", description = "삭제 성공"),
          @ApiResponse(responseCode = "404", description = "직원을 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  ResponseEntity<Void> deleteEmployee(@PathVariable Long id, HttpServletRequest request);

  @Operation(
      summary = "직원 목록 조회",
      description = "직원 목록을 조회합니다.",
      parameters = {
          @Parameter(name = "nameOrEmail", in = ParameterIn.QUERY, description = "직원 이름 또는 이메일", required = false, schema = @Schema(type = "string")),
          @Parameter(name = "employeeNumber", in = ParameterIn.QUERY, description = "사원 번호", required = false, schema = @Schema(type = "string")),
          @Parameter(name = "departmentName", in = ParameterIn.QUERY, description = "부서 이름", required = false, schema = @Schema(type = "string")),
          @Parameter(name = "position", in = ParameterIn.QUERY, description = "직함", required = false, schema = @Schema(type = "string")),
          @Parameter(name = "hireDateFrom", in = ParameterIn.QUERY, description = "입사일 시작", required = false, schema = @Schema(type = "string", format = "date")),
          @Parameter(name = "hireDateTo", in = ParameterIn.QUERY, description = "입사일 종료", required = false, schema = @Schema(type = "string", format = "date")),
          @Parameter(name = "status", in = ParameterIn.QUERY, description = "상태 (재직중, 휴직중, 퇴사)", required = false, schema = @Schema(type = "string", allowableValues = {
              "ACTIVE", "ON_LEAVE", "RESIGNED"})),
          @Parameter(name = "idAfter", in = ParameterIn.QUERY, description = "이전 페이지 마지막 요소 ID", required = false, schema = @Schema(type = "integer", format = "int64")),
          @Parameter(name = "cursor", in = ParameterIn.QUERY, description = "커서 (다음 페이지 시작점)", required = false, schema = @Schema(type = "string")),
          @Parameter(name = "size", in = ParameterIn.QUERY, description = "페이지 크기 (기본값: 10)", required = false, schema = @Schema(type = "integer", format = "int32", defaultValue = "10")),
          @Parameter(name = "sortField", in = ParameterIn.QUERY, description = "정렬 필드 (name, employeeNumber, hireDate)", required = false, schema = @Schema(type = "string", defaultValue = "name")),
          @Parameter(name = "sortDirection", in = ParameterIn.QUERY, description = "정렬 방향 (asc 또는 desc, 기본값: asc)", required = false, schema = @Schema(type = "string", defaultValue = "asc"))
      },
      responses = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
              content = @Content(schema = @Schema(implementation = CursorPageResponseEmployeeDto.class))),
          @ApiResponse(responseCode = "400", description = "잘못된 요청",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  ResponseEntity<CursorPageResponseEmployeeDto> findEmployees(
      @RequestParam(required = false) CursorPageRequestDto cursorPageRequestDto);

  @Operation(
      summary = "직원 상세 조회",
      description = "직원 상세 정보를 조회합니다.",
      responses = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
              content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
          @ApiResponse(responseCode = "404", description = "직원을 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  ResponseEntity<EmployeeDto> findEmployee(
      @Parameter(description = "직원 ID") @PathVariable("id") Long id);

  @Operation(
      summary = "직원 수 조회",
      description = "지정된 조건에 맞는 직원 수를 조회합니다. 상태 필터링 및 입사일 기간 필터링이 가능합니다.",
      parameters = {
          @Parameter(
              name = "status",
              in = ParameterIn.QUERY,
              description = "직원 상태 (재직중, 휴직중, 퇴사)",
              required = false,
              schema = @Schema(type = "string", allowableValues = {"ACTIVE", "ON_LEAVE",
                  "RESIGNED"})
          ),
          @Parameter(
              name = "fromDate",
              in = ParameterIn.QUERY,
              description = "입사일 시작 (지정 시 해당 기간 내 입사한 직원 수 조회, 미지정 시 전체 직원 수 조회)",
              required = false,
              schema = @Schema(type = "string", format = "date")
          ),
          @Parameter(
              name = "toDate",
              in = ParameterIn.QUERY,
              description = "입사일 종료 (fromDate와 함께 사용, 기본값: 현재 일시)",
              required = false,
              schema = @Schema(type = "string", format = "date")
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "조회 성공",
              content = @Content(schema = @Schema(type = "integer", format = "int64"))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
          @ApiResponse(
              responseCode = "500",
              description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          )
      }
  )
  ResponseEntity<Long> countEmployees(
      @RequestParam(required = false) EmployeeStatus status,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate toDate);

  @Operation(
      summary = "직원 수 추이 조회",
      description = "지정된 기간 및 시간 단위로 그룹화된 직원 수 추이를 조회합니다. 파라미터를 제공하지 않으면 최근 12개월 데이터를 월 단위로 반환합니다.",
      parameters = {
          @Parameter(
              name = "from",
              in = ParameterIn.QUERY,
              description = "시작 일시 (기본값: 현재로부터 unit 기준 12개 이전)",
              required = false,
              schema = @Schema(type = "string", format = "date")
          ),
          @Parameter(
              name = "to",
              in = ParameterIn.QUERY,
              description = "종료 일시 (기본값: 현재)",
              required = false,
              schema = @Schema(type = "string", format = "date")
          ),
          @Parameter(
              name = "unit",
              in = ParameterIn.QUERY,
              description = "시간 단위 (day, week, month, quarter, year, 기본값: month)",
              required = false,
              schema = @Schema(type = "string", defaultValue = "month")
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "조회 성공",
              content = @Content(
                  mediaType = "*/*",
                  schema = @Schema(
                      type = "array",
                      implementation = EmployeeTrendDto.class
                  )
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청 또는 지원하지 않는 시간 단위",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
          @ApiResponse(
              responseCode = "500",
              description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          )
      }
  )
  ResponseEntity<List<EmployeeTrendDto>> statusTrendEmployees(
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate from,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate to,
      @RequestParam(required = false, defaultValue = "month") String unit);

  @Operation(
      summary = "직원 분포 조회",
      description = "지정된 기준으로 그룹화된 직원 분포를 조회합니다.",
      parameters = {
          @Parameter(
              name = "groupBy",
              in = ParameterIn.QUERY,
              description = "그룹화 기준 (department: 부서별, position: 직무별, 기본값: department)",
              required = false,
              schema = @Schema(type = "string", defaultValue = "department")
          ),
          @Parameter(
              name = "status",
              in = ParameterIn.QUERY,
              description = "직원 상태 (재직중, 휴직중, 퇴사, 기본값: 재직중)",
              required = false,
              schema = @Schema(type = "string", defaultValue = "ACTIVE", allowableValues = {
                  "ACTIVE", "ON_LEAVE", "RESIGNED"})
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "조회 성공",
              content = @Content(
                  mediaType = "*/*",
                  schema = @Schema(
                      type = "array",
                      implementation = EmployeeDistributionDto.class
                  )
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청 또는 지원하지 않는 그룹화 기준",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          ),
          @ApiResponse(
              responseCode = "500",
              description = "서버 오류",
              content = @Content(schema = @Schema(implementation = ErrorResponse.class))
          )
      }
  )
  ResponseEntity<List<EmployeeDistributionDto>> statusDistributionEmployees(
      @RequestParam(required = false, defaultValue = "department") String groupBy,
      @RequestParam(required = false, defaultValue = "ACTIVE") EmployeeStatus status);
}
