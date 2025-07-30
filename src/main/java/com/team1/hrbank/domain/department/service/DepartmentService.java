package com.team1.hrbank.domain.department.service;

import com.team1.hrbank.domain.department.Mapper.DepartmentMapper;
import com.team1.hrbank.domain.department.dto.DepartmentDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.repository.DepartmentRepository;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final DepartmentMapper departmentMapper;


  public DepartmentDto create(DepartmentCreateRequestDto departmentCreateRequestDto) {
    Department departmentEntity = departmentMapper.toDepartment(departmentCreateRequestDto);
    if (departmentRepository.existsByName(departmentEntity.getName())) {
      throw new IllegalArgumentException("중복된 부서명이 존재 합니다.");
    }

    Department savedDepartment = departmentRepository.save(departmentEntity);
    // 부서에 소속된 직원 수 조회가 완성 됐을 시 employeeRepository 참조하여 아래 메서드를 return 으로 변경
    // DepartmentDto departmentDto = this.toDepartmentDto(savedDepartment);

    return departmentMapper.toDepartmentDto(savedDepartment);
  }


  public DepartmentDto update(long id, DepartmentUpdateRequestDto departmentUpdateRequestDto) {
    Department departmentEntity = departmentMapper.toDepartment(departmentUpdateRequestDto);

    Department department = departmentRepository.findById(departmentEntity.getId())
        .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

    if (departmentRepository.existsByName(departmentEntity.getName())) {
      throw new IllegalArgumentException("중복된 부서명이 존재 합니다.");
    }

    department.setName(departmentEntity.getName());
    department.setDescription(departmentEntity.getDescription());
    department.setEstablishedDate(departmentEntity.getEstablishedDate());

    Department updatedDepartment = departmentRepository.save(department);

    // 부서에 소속된 직원 수 조회가 완성 됐을 시 employeeRepository 참조하여 아래 메서드를 return 으로 변경 create 와 동일
    // DepartmentDto departmentDto = this.toDepartmentDto(savedDepartment);

    return departmentMapper.toDepartmentDto(updatedDepartment);
  }

  public List<DepartmentDto> findAll() {
    return List.of();
  }

  public DepartmentDto findById(long id) {
    return null;
  }

  public void delete(long id) {

  }

  // 부서별 직원 수 조회용 변환 메서드 분리
   /* private DepartmentDto toDepartmentDto(Department department) {
    int employeeCount = (int) employeeRepository.countByDepartmentId(department.getId());
    return new DepartmentDto(
        department.getId(),
        department.getName(),
        department.getDescription(),
        department.getEstablishedDate(),
        employeeCount
    );
  } */
}