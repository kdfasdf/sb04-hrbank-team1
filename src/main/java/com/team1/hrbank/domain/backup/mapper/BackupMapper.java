package com.team1.hrbank.domain.backup.mapper;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.entity.Backup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BackupMapper {


  @Mapping(target = "fileId", source = "fileMetadata.id")
  @Mapping(target = "startedAt", source = "createdAt")
  BackupDto toDto(Backup backup);
}
