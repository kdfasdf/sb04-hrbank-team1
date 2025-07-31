package com.team1.hrbank.domain.department.mapper;

import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  Department toEntity(DepartmentCreateRequestDto departmentCreateRequestDto);

  Department toEntity(Long id, DepartmentUpdateRequestDto departmentUpdateRequestDto);

  DepartmentDto toDepartmentDto(Department departmentEntity);

}
