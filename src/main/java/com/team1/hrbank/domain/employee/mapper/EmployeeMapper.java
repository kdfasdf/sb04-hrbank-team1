package com.team1.hrbank.domain.employee.mapper;

import com.team1.hrbank.domain.employee.dto.EmployeeDto;
import com.team1.hrbank.domain.employee.dto.request.EmployeeUpdateRequestDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import com.team1.hrbank.domain.employee.dto.request.EmployeeCreateRequestDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  @Mapping(source = "department.id", target = "departmentId")
  @Mapping(source = "department.name", target = "departmentName")
  @Mapping(source = "fileMetaData.id", target = "profileImageId")
  EmployeeDto toEmployeeDto(Employee employee);

  // EmployeeCreateRequest는 커스텀 매핑이 필요해서 @Mapping 사용
  @Mapping(target = "employeeNumber", source = "employeeNumber")
  @Mapping(target = "status", source = "employeeStatus")
  @Mapping(target = "department", source = "department")
  @Mapping(target = "fileMetaData", source = "fileMetadata")
  @Mapping(target = "name", source = "employeeCreateRequest.name")
  @Mapping(target = "email", source = "employeeCreateRequest.email")
  @Mapping(target = "position", source = "employeeCreateRequest.position")
  @Mapping(target = "hireDate", source = "employeeCreateRequest.hireDate")
  Employee toEmployee(EmployeeCreateRequestDto employeeCreateRequestDto,
      String employeeNumber,
      EmployeeStatus employeeStatus,
      Department department,
      FileMetadata fileMetadata);
}