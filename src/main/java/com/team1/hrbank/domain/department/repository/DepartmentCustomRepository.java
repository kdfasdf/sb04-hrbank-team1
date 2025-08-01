package com.team1.hrbank.domain.department.repository;

import com.team1.hrbank.domain.department.dto.request.DepartmentSearchRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import java.util.List;

public interface DepartmentCustomRepository {

  List<DepartmentDto> searchDepartments(DepartmentSearchRequestDto request);

  long countDepartments(String keyword);

}
