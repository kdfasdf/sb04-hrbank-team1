package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.file.dto.StoredFileInfo;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.entity.FileType;
import com.team1.hrbank.domain.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

  private final FileMetadataRepository fileMetadataRepository;
  private final FileStorageManager fileStorageManager;

  @Transactional
  public FileMetadata uploadProfileImage(MultipartFile file) {

    StoredFileInfo stored = fileStorageManager.uploadProfileImage(file);
    FileMetadata metadata = FileMetadata.builder()
        .originalName(file.getOriginalFilename())
        .savedName(stored.file().getName())
        .extension(stored.extension())
        .fileType(FileType.PROFILE)
        .fileSize(file.getSize())
        .filePath(String.valueOf(stored.file().getAbsoluteFile()))
        .build();

    return fileMetadataRepository.save(metadata);
  }

  @Transactional
  public FileMetadata generateBackupFile(Long backupId, String backupContent) {

    StoredFileInfo stored = fileStorageManager.generateBackupFile(backupId, backupContent);
    FileMetadata metadata = FileMetadata.builder()
        .originalName(stored.file().getName())
        .savedName(stored.file().getName())
        .extension(stored.extension())
        .fileType(FileType.BACKUP)
        .fileSize(stored.file().length())
        .filePath(String.valueOf(stored.file().getAbsoluteFile()))
        .build();

    return fileMetadataRepository.save(metadata);
  }
}
