package com.team1.hrbank.domain.department.service;

import com.team1.hrbank.domain.department.dto.request.DepartmentSearchRequestDto;
import com.team1.hrbank.domain.department.dto.response.DepartmentPageResponseDto;
import com.team1.hrbank.domain.department.mapper.DepartmentMapper;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.department.repository.DepartmentRepository;
import com.team1.hrbank.domain.department.dto.request.DepartmentCreateRequestDto;
import com.team1.hrbank.domain.department.dto.request.DepartmentUpdateRequestDto;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import java.util.List;
import java.util.NoSuchElementException;
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
    Department departmentEntity = departmentMapper.toEntity(departmentCreateRequestDto);
    if (departmentRepository.existsByName(departmentEntity.getName())) {
      throw new IllegalArgumentException("중복된 부서명이 존재 합니다.입력한 부서명 : " + departmentEntity.getName());
    }

    Department savedDepartment = departmentRepository.save(departmentEntity);

    return departmentMapper.toDepartmentDto(savedDepartment);
  }


  public DepartmentDto update(Long id, DepartmentUpdateRequestDto departmentUpdateRequestDto) {
    Department departmentEntity = departmentMapper.toEntity(id, departmentUpdateRequestDto);

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

  @Transactional(readOnly = true)
  public DepartmentPageResponseDto getDepartments(
      DepartmentSearchRequestDto departmentSearchRequestDto) {
    List<DepartmentDto> content = departmentRepository.searchDepartments(
        departmentSearchRequestDto);

    Long nextIdAfter = content.isEmpty() ? null : content.get(content.size() - 1).id();
    boolean hasNextPage = content.size() == (departmentSearchRequestDto.size() != null ? departmentSearchRequestDto.size() : 10);

    long totalElements = departmentRepository.countDepartments((departmentSearchRequestDto.nameOrDescription()));

    String nextCursor = nextIdAfter != null ? nextIdAfter.toString() : null;
    return new DepartmentPageResponseDto(
        content,
        nextCursor,
        nextIdAfter,
        departmentSearchRequestDto.size() != null ? departmentSearchRequestDto.size() : 10,
        totalElements,
        hasNextPage
    );
  }

  @Transactional(readOnly = true)
  public DepartmentDto findById(Long id) {
    return departmentRepository.findById(id).map(this::toDepartmentDto)
        .orElseThrow(() -> new NoSuchElementException("부서를 찾을 수 없습니다. ID : " + id));
  }

  public void delete(Long id) {
    Department department = departmentRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("부서를 찾을 수 없습니다. ID : " + id));

    long employeeCount = employeeRepository.countByDepartmentId(department.getId());

    if(employeeCount > 0) {
      throw new IllegalArgumentException("부서에 소속된 직원이 있어 삭제할 수 없습니다. ID : " + id);
    }

    departmentRepository.delete(department);
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
