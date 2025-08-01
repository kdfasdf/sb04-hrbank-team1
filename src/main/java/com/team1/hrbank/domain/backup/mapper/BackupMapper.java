package com.team1.hrbank.domain.backup.mapper;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.entity.Backup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BackupMapper {

  BackupDto toDto(Backup backup);
}
