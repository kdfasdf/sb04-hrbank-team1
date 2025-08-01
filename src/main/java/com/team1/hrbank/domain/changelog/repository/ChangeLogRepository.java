package com.team1.hrbank.domain.changelog.repository;

import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {

  Optional<ChangeLog> findFirstByUpdatedAtDesc();
}
