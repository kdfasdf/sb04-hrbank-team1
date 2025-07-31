package com.team1.hrbank.domain.changelog.repository;

import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogDiffRepository extends JpaRepository<ChangeLogDiff, Long> {
}
