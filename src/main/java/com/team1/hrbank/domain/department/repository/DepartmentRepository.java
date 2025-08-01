package com.team1.hrbank.domain.department.repository;

import com.team1.hrbank.domain.department.entity.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

  boolean existsByName(String name);

//  Optional<Department> findDepartmentByDepartmentId(long departmentId);
}
