package com.team1.hrbank.domain.changelog.service;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDiffDto;
import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import com.team1.hrbank.domain.changelog.mapper.ChangeLogDiffMapper;
import com.team1.hrbank.domain.changelog.repository.ChangeLogDiffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeLogDiffService {
    private final ChangeLogDiffRepository changeLogDiffRepository;
    private final ChangeLogDiffMapper changeLogDiffMapper;

    public List<ChangeLogDiffDto> findAllByChangeLogId(Long changeLogId) {
        List<ChangeLogDiff> changeLogDiffs = changeLogDiffRepository.findAllByChangeLogId(changeLogId);
        return changeLogDiffs.stream()
                .map(changeLogDiffMapper::toDto)
                .toList();
    }
}
