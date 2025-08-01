package com.team1.hrbank.domain.backup.repository;

import com.team1.hrbank.domain.backup.entity.Backup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BackupRepository extends JpaRepository<Backup, Long> {

  @Query("SELECT b FROM Backup b order by b.endedAt desc")
  Optional<Backup> findFirstByEndedAtDesc();
}
