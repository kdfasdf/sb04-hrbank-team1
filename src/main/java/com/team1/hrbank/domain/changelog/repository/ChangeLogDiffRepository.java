package com.team1.hrbank.domain.changelog.repository;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDiffDto;
import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeLogDiffRepository extends JpaRepository<ChangeLogDiff, Long> {
        List<ChangeLogDiff> findAllByChangeLogId(Long changeLogId);
}
