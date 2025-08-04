package com.team1.hrbank.domain.backup.repository;

import com.team1.hrbank.domain.backup.entity.Backup;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BackupRepository extends JpaRepository<Backup, Long>,
    JpaSpecificationExecutor<Backup> {

  @Query("select b from Backup b order by b.createdAt desc limit 1")
  Optional<Backup> findFirstOrderByCreatedAtDesc();

  @Query("select b from Backup b where b.status = :status order by b.createdAt desc limit 1")
  Optional<Backup> findFirstByStatusOrderByCreatedAtDesc(@Param("status") BackupStatus status);
}
