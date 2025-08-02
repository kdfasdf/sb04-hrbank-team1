package com.team1.hrbank.domain.backup.repository;

import com.team1.hrbank.domain.backup.entity.Backup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BackupRepository extends JpaRepository<Backup, Long>,
    JpaSpecificationExecutor<Backup> {

  @Query("select b from Backup b order by b.createdAt desc")
  Optional<Backup> findFirstOrderByEndedAtDesc();

  @Query("select b from Backup b where b.status = :status order by b.createdAt desc")
  Optional<Backup> findFirstByStatusOrderByCreatedAtDesc(String status);
}
