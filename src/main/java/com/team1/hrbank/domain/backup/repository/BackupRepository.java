package com.team1.hrbank.domain.backup.repository;

import com.team1.hrbank.domain.backup.entity.Backup;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BackupRepository extends JpaRepository<Backup, Long>,
    JpaSpecificationExecutor<Backup> {

  Optional<Backup> findFirstByOrderByCreatedAtDesc();

  Optional<Backup> findFirstByStatusOrderByCreatedAtDesc(BackupStatus status);
}
