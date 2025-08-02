package com.team1.hrbank.domain.employee.dto;

import com.team1.hrbank.domain.employee.entity.EmployeeStatus;
import java.time.LocalDate;

public record EmployeeDto(
    Long id,                 // 직원 ID
    String name,             // 직원 이름
    String email,            // 직원 이메일
    String employeeNumber,   // 사원 번호
    Long departmentId,       // 부서 ID
    String departmentName,   // 부서 이름
    String position,         // 직함
    LocalDate hireDate,      // 입사일
    EmployeeStatus status,   // 상태 (ACTIVE, ON_LEAVE, RESIGNED)
    Long profileImageId      // 프로필 이미지 ID
) {

}
