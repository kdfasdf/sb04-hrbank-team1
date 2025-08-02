package com.team1.hrbank.domain.changelog.dto.response;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDto;

import java.util.List;

public record ChangeLogSearchResponse(
        List<ChangeLogDto> content,
        String nextCursor,
        Long nextIdAfter,
        int size,
        Long totalElements,
        boolean hasNext
) {
}
