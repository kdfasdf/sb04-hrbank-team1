package com.team1.hrbank.domain.department.entity;

import com.team1.hrbank.domain.base.BaseEntity;
import com.team1.hrbank.domain.department.dto.response.DepartmentDto;
import com.team1.hrbank.domain.employee.entity.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "departments")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SqlResultSetMapping(
    name = "DepartmentDtoMapping",
    classes = @ConstructorResult(
        targetClass = DepartmentDto.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "description", type = String.class),
            @ColumnResult(name = "established_date", type = LocalDate.class),
            @ColumnResult(name = "employee_count", type = Integer.class)
        }
    )
)
public class Department extends BaseEntity {

  @Column(name = "name", nullable = false, length = 50)
  private String name;                       // 부서 이름

  @Column(name = "description", nullable = false, length = 255)
  private String description;                // 부서 설명

  @Column(name = "established_date", nullable = false)
  private LocalDate establishedDate;     // 부서 설립일

  @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
  private List<Employee> employees = new ArrayList<>();

}
