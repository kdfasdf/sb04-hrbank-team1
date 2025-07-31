package com.team1.hrbank.domain.department.service;

import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import java.util.List;

public interface DepartmentService {

  DepartmentDto create(DepartmentCreateRequestDto departmentCreateRequestDto);

  DepartmentDto update(DepartmentUpdateRequestDto departmentUpdateRequestDto);

  List<DepartmentDto> findAll();

  DepartmentDto findById(long id);

  void delete(long id);


}
