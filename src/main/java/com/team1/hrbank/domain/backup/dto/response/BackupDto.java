package com.team1.hrbank.domain.backup.dto.response;

import com.team1.hrbank.domain.backup.entity.BackupStatus;
import java.time.LocalDateTime;

public record BackupDto(
    Long id,
    String worker,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    BackupStatus status,
    Long fileId
) {

}

