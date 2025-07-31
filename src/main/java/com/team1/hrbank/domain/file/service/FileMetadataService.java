package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.file.dto.response.FileMetadataDto;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.entity.FileType;
import com.team1.hrbank.domain.file.entity.FileUsageType;
import com.team1.hrbank.domain.file.mapper.FileMetadataMapper;
import com.team1.hrbank.domain.file.repository.FileMetadataRepository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileMetadataService {

  private final FileMetadataRepository fileMetadataRepository;
  private final FileMetadataMapper fileMetadataMapper;

  private static final List<String> ALLOWED_PROFILE_EXTENSIONS = List.of("png", "jpg", "jpeg");
  private static final String PROFILE_PATH = "uploads/profile";
  private static final String BACKUP_PATH = "uploads/backup";

  @Transactional
  public FileMetadataDto uploadProfileImage(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    validateOriginalFileName(originalFilename);

    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
        .toLowerCase();
    validateAllowedProfileExtension(extension);

    String rootPath = System.getProperty("user.dir");
    File uploadDir = new File(rootPath, PROFILE_PATH);
    ensureDirectoryExists(uploadDir);

    String savedName = UUID.randomUUID() + "." + extension;
    File destFile = saveFileToDirectory(file, uploadDir, savedName);

    FileMetadata metadata = FileMetadata.builder()
        .originalName(originalFilename)
        .savedName(savedName)
        .fileType(FileType.valueOf(extension.toUpperCase()))
        .fileUsageType(FileUsageType.PROFILE)
        .fileSize(file.getSize())
        .filePath(destFile.getAbsolutePath())
        .build();

    FileMetadata savedProfileImage = fileMetadataRepository.save(metadata);

    return fileMetadataMapper.mapToDto(savedProfileImage);
  }

  public FileMetadataDto generateBackupFile(String backupContent) {
    String extension = "csv";
    String savedName = UUID.randomUUID() + "." + extension;

    String rootPath = System.getProperty("user.dir");
    File backupDir = new File(rootPath, BACKUP_PATH);
    ensureDirectoryExists(backupDir);

    File destFile = writeCsvFile(backupDir, savedName, backupContent);

    FileMetadata metadata = FileMetadata.builder()
        .originalName(savedName)
        .savedName(savedName)
        .fileType(FileType.CSV)
        .fileUsageType(FileUsageType.BACKUP)
        .fileSize(destFile.length())
        .filePath(destFile.getAbsolutePath())
        .build();

    FileMetadata savedBackupFile = fileMetadataRepository.save(metadata);

    return fileMetadataMapper.mapToDto(savedBackupFile);
  }


  // 공통 메서드
  private void validateOriginalFileName(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      throw new IllegalArgumentException("올바르지 않은 파일명");
    }
  }

  private void validateAllowedProfileExtension(String extension) {
    if (!ALLOWED_PROFILE_EXTENSIONS.contains(extension)) {
      throw new IllegalArgumentException("png, jpg, jpeg만 가능, 현재 확장자: " + extension);
    }
  }

  private void ensureDirectoryExists(File dir) {
    if (!dir.exists() && !dir.mkdirs()) {
      throw new RuntimeException("디렉토리 생성 실패");
    }
  }

  private File saveFileToDirectory(MultipartFile file, File dir, String savedName) {
    File destFile = new File(dir, savedName);
    try {
      file.transferTo(destFile); // 실제 파일 저장
      return destFile;
    } catch (IOException e) {
      throw new RuntimeException("파일 저장 중 오류 발생", e);
    }
  }

  private File writeCsvFile(File dir, String savedName, String backupContent) {
    File destFile = new File(dir, savedName);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {
      writer.write(backupContent);
      return destFile;
    } catch (IOException e) {
      throw new RuntimeException("CSV 파일 저장 실패", e);
    }
  }
}
