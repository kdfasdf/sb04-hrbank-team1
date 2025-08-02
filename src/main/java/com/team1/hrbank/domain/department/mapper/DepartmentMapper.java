package com.team1.hrbank.domain.department.mapper;

import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  @Mapping(target = "employees", ignore = true)
  Department toEntity(DepartmentCreateRequestDto departmentCreateRequestDto);

  @Mapping(target = "employees", ignore = true)
  Department toEntity(Long id, DepartmentUpdateRequestDto departmentUpdateRequestDto);

  @Mapping(target = "employeeCount", source = ".", qualifiedByName = "mapEmployeeCount")
  DepartmentDto toDepartmentDto(Department departmentEntity);

  //직원 수 계산 로직 (null-safe)
  @Named("mapEmployeeCount")
  default int mapEmployeeCount(Department department) {
    if (department.getEmployees() == null) {
      return 0;
    }
    return department.getEmployees().size();
  }
}
