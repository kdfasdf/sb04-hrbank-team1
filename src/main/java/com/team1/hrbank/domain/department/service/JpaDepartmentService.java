package com.team1.hrbank.domain.department.service;

import com.team1.hrbank.domain.department.dto.DepartmentDto;
import com.team1.hrbank.domain.department.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.request.DepartmentUpdateRequestDto;
import java.util.List;

public interface JpaDepartmentService {

  DepartmentDto create(DepartmentCreateRequestDto departmentCreateRequestDto);

  DepartmentDto update(DepartmentUpdateRequestDto departmentUpdateRequestDto);

  List<DepartmentDto> findAll();

  DepartmentDto findById(long id);

  void delete(long id);


}
