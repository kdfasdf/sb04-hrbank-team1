package com.team1.hrbank.domain.changelog.dto.request;

import com.team1.hrbank.domain.changelog.entity.ChangeLogType;

import java.time.LocalDateTime;

public record ChangeLogSearchRequest(
        String employeeNumber,
        String memo,
        String ipAddress,
        LocalDateTime from,
        LocalDateTime to,
        ChangeLogType type,
        SortKey sortKey,
        Long lastId //무한 스크롤 구현용
) {
    public enum SortKey {
        CREATED_AT_DESC,
        CREATED_AT_ASC,
        IP_ADDRESS_DESC,
        IP_ADDRESS_ASC
    }
}