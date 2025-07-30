package com.team1.hrbank.domain.changelog.entity;

import com.team1.hrbank.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "change_log_diffs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeLogDiff extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "change_log_id", nullable = false)
    private ChangeLog changeLog;

    @Column(name = "property_name", nullable = false, length = 30)
    private String propertyName;

    @Column(name = "before_value", length = 255)
    private String beforeValue;

    @Column(name = "after_value", length = 255)
    private String afterValue;

}
