package com.team1.hrbank.domain.file.mapper;

import com.team1.hrbank.domain.file.dto.FileMetadataDto;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {

  FileMetadataDto mapToDto(FileMetadata fileMetadata);
}
