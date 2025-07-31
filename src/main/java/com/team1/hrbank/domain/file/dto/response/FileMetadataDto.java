package com.team1.hrbank.domain.file.dto.response;

public record FileMetadataDto(
    String originalName,
    String savedName,
    String extension,
    String fileType,
    Long fileSize,
    String filePath
) {

}
