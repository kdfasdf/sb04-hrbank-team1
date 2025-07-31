package com.team1.hrbank.domain.changelog.entity;

import com.team1.hrbank.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "change_logs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChangeLog extends BaseEntity {

    @Column(nullable = false, length = 50, name = "employee_number")
    private String employeeNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ChangeLogType type;

    @Column(nullable = true, length = 255)
    private String memo;

    @Column(nullable = false, length = 50, name = "ip_address")
    private String ipAddress;
}