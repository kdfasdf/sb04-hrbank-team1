package com.team1.hrbank.domain.changelog.mapper;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDto;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface ChangeLogMapper {

    ChangeLogDto toDto(ChangeLog changeLog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ChangeLog toEntity(ChangeLogDto dto);
}
