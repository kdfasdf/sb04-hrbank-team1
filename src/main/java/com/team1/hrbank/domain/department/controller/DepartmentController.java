package com.team1.hrbank.domain.department.controller;

import com.team1.hrbank.domain.department.dto.request.DepartmentSearchRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentPageResponseDto;
import com.team1.hrbank.domain.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "부서 관리", description = "부서 관리 API")
public class DepartmentController {

  private final DepartmentService departmentService;

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  @Operation(summary = "부서 등록", description = "새로운 부서를 등록합니다.")
  public ResponseEntity<DepartmentDto> create(
      @RequestBody DepartmentCreateRequestDto departmentCreateRequestDto) {
    DepartmentDto department = departmentService.create(departmentCreateRequestDto);
    return ResponseEntity.ok(department);
  }

  @PatchMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
  @Operation(summary = "부서 수정", description = "부서 정보를 수정합니다.")
  public ResponseEntity<DepartmentDto> update(
      @PathVariable("id") @Parameter(description = "부서 ID") Long id,
      @RequestBody DepartmentUpdateRequestDto departmentUpdateRequestDto
  ) {
    DepartmentDto department = departmentService.update(id, departmentUpdateRequestDto);
    return ResponseEntity.ok(department);
  }

  @GetMapping(path = "/{id}")
  @Operation(summary = "부서 상세 조회", description = "부서 상세 정보를 조회합니다.")
  public ResponseEntity<DepartmentDto> findOne(
      @PathVariable("id") @Parameter(description = "부서 ID") Long id) {
    DepartmentDto findOne = departmentService.findById(id);
    return ResponseEntity.ok(findOne);
  }

  @GetMapping
  @Operation(summary = "부서 목록 조회", description = "부서 목록을 조회합니다.")
  public ResponseEntity<DepartmentPageResponseDto> findDepartments(
      @RequestParam(required = false) @Parameter(description = "부서 이름 또는 설명") String nameOrDescription,
      @RequestParam(required = false) @Parameter(description = "이전 페이지의 마지막 요소 ID") Long idAfter,
      @RequestParam(required = false) @Parameter(description = "커서(다음 페이지 시작점)") String cursor,
      @RequestParam(required = false, defaultValue = "10") @Parameter(description = "페이지 크기(기본값: 10)") Integer size,
      @RequestParam(required = false, defaultValue = "establishedDate") @Parameter(description = "정렬 필드(name 또는 establishedDate)") String sortField,
      @RequestParam(required = false, defaultValue = "asc") @Parameter(description = "정렬 방향 (asc 또는 desc, 기본값 : asc)") String sortDirection
  ) {

    DepartmentSearchRequestDto requestDto = new DepartmentSearchRequestDto(nameOrDescription,
        idAfter, cursor, size, sortField, sortDirection);

    DepartmentPageResponseDto responseDto = departmentService.getDepartments(requestDto);

    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping(path = "{id}")
  @Operation(summary = "부서 삭제", description = "부서를 삭제 합니다.")
  public ResponseEntity<Void> delete(@PathVariable("id") @Parameter(description = "부서 ID") Long id
  ) {
    departmentService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
