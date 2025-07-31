package com.team1.hrbank.domain.file.dto;

public record FileMetadataDto(
    String originalName,
    String savedName,
    String fileType,
    String fileUsageType,
    Long fileSize,
    String filePath
) {

}
