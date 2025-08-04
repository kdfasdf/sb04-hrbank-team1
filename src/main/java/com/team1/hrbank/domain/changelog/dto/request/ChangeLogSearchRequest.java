package com.team1.hrbank.domain.changelog.dto.request;

import com.team1.hrbank.domain.changelog.entity.ChangeLogType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ChangeLogSearchRequest(
        String employeeNumber,
        String memo,
        String ipAddress,
        ChangeLogType type,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime from,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime to,
        Long lastId,
        String sortField,
        String sortDirection,
        Integer size
) {
}
