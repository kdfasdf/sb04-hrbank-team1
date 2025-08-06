package com.team1.hrbank.domain.employee.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "직원 목록 커서 기반 요청 DTO")
public record CursorPageRequestDto(

    @Schema(description = "직원 이름 또는 이메일")
    String nameOrEmail,

    @Schema(description = "사원 번호")
    String employeeNumber,

    @Schema(description = "부서 이름")
    String departmentName,

    @Schema(description = "직함")
    String position,

    @Schema(description = "입사일 시작", example = "2023-01-01")
    LocalDate hireDateFrom,

    @Schema(description = "입사일 종료", example = "2024-12-31")
    LocalDate hireDateTo,

    @Schema(description = "상태 (ACTIVE, ON_LEAVE, RESIGNED)")
    String status,

    @Schema(description = "이전 페이지 마지막 요소 ID")
    Long idAfter,

    @Schema(description = "커서 (다음 페이지 시작점)")
    String cursor,

    @Schema(description = "페이지 크기 (기본값: 10)", defaultValue = "10")
    Integer size,

    @Schema(description = "정렬 필드 (name, employeeNumber, hireDate)", defaultValue = "name")
    String sortField,

    @Schema(description = "정렬 방향 (asc 또는 desc)", defaultValue = "asc")
    String sortDirection

) {

}