package com.team1.hrbank.domain.changelog.dto.response;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDto;

import java.util.List;

public record ChangeLogSearchResponse(
        List<ChangeLogDto> data,
        boolean hasNext,
        Long nextCursor
) {
}
