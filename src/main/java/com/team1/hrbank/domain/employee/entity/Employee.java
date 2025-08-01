package com.team1.hrbank.domain.employee.entity;

import com.team1.hrbank.domain.base.BaseEntity;
import com.team1.hrbank.domain.department.entity.Department;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Employee extends BaseEntity {

  @Column(name = "employee_number", nullable = false, unique = true, length = 50)
  private String employeeNumber;      // 사원 번호

  @Column(name = "name", nullable = false, length = 50)
  private String name;               // 직원 이름

  @Column(name = "email", nullable = false, unique = true, length = 50)
  private String email;               // 직원 이메일

  @Column(name = "position", nullable = false, length = 30)
  private String position;            // 직함

  @Column(name = "hire_date", nullable = false)
  private LocalDate hireDate;         // 입사일

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 10)
  private EmployeeStatus status;        // 상태 (ACTIVE, ON_LEAVE, RESIGNED)

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "department_id")
  private Department department;  // 부서 ID

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "metadata_id")
  private FileMetadata fileMetaData;  // 프로필 이미지 ID
}
