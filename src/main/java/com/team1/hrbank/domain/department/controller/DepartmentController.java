package com.team1.hrbank.domain.department.controller;

import com.team1.hrbank.domain.department.dto.request.DepartmentSearchRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentPageResponseDto;
import com.team1.hrbank.domain.department.service.DepartmentService;
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
public class DepartmentController {

  private final DepartmentService departmentService;

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<DepartmentDto> create(
      @RequestBody DepartmentCreateRequestDto departmentCreateRequestDto) {
    DepartmentDto department = departmentService.create(departmentCreateRequestDto);
    return ResponseEntity.ok(department);
  }

  @PatchMapping(path = "{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<DepartmentDto> update(@PathVariable("id") Long id,
      @RequestBody DepartmentUpdateRequestDto departmentUpdateRequestDto
  ) {
    DepartmentDto department = departmentService.update(id, departmentUpdateRequestDto);
    return ResponseEntity.ok(department);
  }

  @GetMapping(path = "{id}")
  public ResponseEntity<DepartmentDto> findOne(@PathVariable("id") Long id) {
    DepartmentDto findOne = departmentService.findById(id);
    return ResponseEntity.ok(findOne);
  }

  @GetMapping
  public ResponseEntity<DepartmentPageResponseDto> findDepartments(
      @RequestParam(required = false) String nameOrDescription,
      @RequestParam(required = false) Long idAfter,
      @RequestParam(required = false) String cursor,
      @RequestParam(required = false, defaultValue = "10") Integer size,
      @RequestParam(required = false, defaultValue = "establishedDate") String sortField,
      @RequestParam(required = false, defaultValue = "asc") String sortDirection
  ) {

    DepartmentSearchRequestDto requestDto = new DepartmentSearchRequestDto(nameOrDescription,
        idAfter, cursor, size, sortField, sortDirection);

    DepartmentPageResponseDto responseDto = departmentService.getDepartments(requestDto);

    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id
  ) {
    departmentService.delete(id);
  return ResponseEntity.noContent().build();
  }

}
