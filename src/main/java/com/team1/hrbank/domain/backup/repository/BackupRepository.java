package com.team1.hrbank.domain.backup.repository;

import com.team1.hrbank.domain.backup.entity.Backup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackupRepository extends JpaRepository<Backup, Long> {

  Optional<Backup> findFirstByEndedAtDesc();
}
