package com.team1.hrbank.domain.department.service;

import com.team1.hrbank.domain.department.mapper.DepartmentMapper;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.repository.DepartmentRepository;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final DepartmentMapper departmentMapper;
  private final EmployeeRepository employeeRepository;


  public DepartmentDto create(DepartmentCreateRequestDto departmentCreateRequestDto) {
    Department departmentEntity = departmentMapper.toDepartment(departmentCreateRequestDto);
    if (departmentRepository.existsByName(departmentEntity.getName())) {
      throw new IllegalArgumentException("중복된 부서명이 존재 합니다.입력한 부서명 : " + departmentEntity.getName());
    }

    Department savedDepartment = departmentRepository.save(departmentEntity);

    return departmentMapper.toDepartmentDto(savedDepartment);
  }


  public DepartmentDto update(Long id, DepartmentUpdateRequestDto departmentUpdateRequestDto) {
    Department departmentEntity = departmentMapper.toDepartment(id, departmentUpdateRequestDto);

    Department department = departmentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

    if (!department.getName().equals(departmentEntity.getName())
        && departmentRepository.existsByName(departmentEntity.getName())) {
      throw new IllegalArgumentException(
          "중복된 부서명이 존재 합니다. 입력한 부서명 : " + departmentEntity.getName());
    }

    department.setName(departmentEntity.getName());
    department.setDescription(departmentEntity.getDescription());
    department.setEstablishedDate(departmentEntity.getEstablishedDate());

    Department updatedDepartment = departmentRepository.save(department);

    return toDepartmentDto(updatedDepartment);
  }

  public List<DepartmentDto> findAll() {
    return List.of();
  }

  @Transactional(readOnly = true)
  public DepartmentDto findById(Long id) {
    return departmentRepository.findById(id).map(this::toDepartmentDto)
        .orElseThrow(() -> new NoSuchElementException("부서를 찾을 수 없습니다. ID : " + id));
  }

  public void delete(long id) {

  }

  // 부서별 직원 수 조회용 변환 메서드 분리
  private DepartmentDto toDepartmentDto(Department department) {
    int employeeCount = (int) employeeRepository.countByDepartmentId(department.getId());
    return new DepartmentDto(
        department.getId(),
        department.getName(),
        department.getDescription(),
        department.getEstablishedDate(),
        employeeCount
    );
  }
}
