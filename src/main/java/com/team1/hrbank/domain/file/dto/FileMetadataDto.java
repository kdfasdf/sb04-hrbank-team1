package com.team1.hrbank.domain.file.dto;

public record FileMetadataDto(
    String fileName,
    String fileType,
    String fileUsageType,
    Long fileSize,
    String filePath
) {

}
