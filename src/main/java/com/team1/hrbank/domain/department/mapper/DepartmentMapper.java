package com.team1.hrbank.domain.department.mapper;

import com.team1.hrbank.domain.department.dto.DepartmentDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  Department toDepartment(DepartmentCreateRequestDto departmentCreateRequestDto);

  Department toDepartment(DepartmentUpdateRequestDto departmentUpdateRequestDto);

  DepartmentDto toDepartmentDto(Department departmentEntity);

}
