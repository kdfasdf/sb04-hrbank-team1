package com.team1.hrbank.domain.backup.entity;

import com.team1.hrbank.domain.base.BaseEntity;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "backups")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Backup extends BaseEntity {

    @Column(name = "worker", nullable = false, length = 15)
    private String worker; //ip

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private BackupStatus status;    //진행중,완료,건너뜀,실패

    @Column(name = "endedAt", nullable = false)
    private LocalDateTime endedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metadata_id", nullable = false)
    private FileMetadata metadata;

}
