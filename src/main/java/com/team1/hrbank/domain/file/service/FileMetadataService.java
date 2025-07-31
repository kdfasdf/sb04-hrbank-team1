package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.file.dto.response.FileMetadataDto;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.entity.FileType;
import com.team1.hrbank.domain.file.entity.FileUsageType;
import com.team1.hrbank.domain.file.mapper.FileMetadataMapper;
import com.team1.hrbank.domain.file.repository.FileMetadataRepository;
import java.io.File;
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
  private static final List<String> ALLOWED_BACKUP_EXTENSIONS = List.of("csv");
  private static final String PROFILE_PATH = "uploads/profile";
  private static final String BACKUP_PATH = "uploads/backup";

  @Transactional
  public FileMetadataDto uploadFile(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    validateOriginalFileName(originalFilename);

    String extension = extractExtension(originalFilename);
    FileUsageType fileUsageType = determineFileUsageType(extension);

    String rootPath = System.getProperty("user.dir");
    String uploadSubPath = fileUsageType == FileUsageType.PROFILE ? PROFILE_PATH : BACKUP_PATH;
    File uploadDir = new File(rootPath, uploadSubPath);
    ensureDirectoryExists(uploadDir);

    String savedName = UUID.randomUUID() + "." + extension;
    File destFile = saveFileToDirectory(file, uploadDir, savedName);

    FileMetadata metadata = FileMetadata.builder()
        .originalName(originalFilename)
        .savedName(savedName)
        .fileType(FileType.valueOf(extension.toUpperCase()))
        .fileUsageType(fileUsageType)
        .fileSize(file.getSize())
        .filePath(destFile.getAbsolutePath())
        .build();

    FileMetadata savedFile = fileMetadataRepository.save(metadata);

    return fileMetadataMapper.mapToDto(savedFile);
  }

  // 공통 메서드
  private void validateOriginalFileName(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      throw new IllegalArgumentException("올바르지 않은 파일명");
    }
  }

  private String extractExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
  }

  private FileUsageType determineFileUsageType(String extension) {
    if (ALLOWED_PROFILE_EXTENSIONS.contains(extension)) {
      return FileUsageType.PROFILE;
    } else if (ALLOWED_BACKUP_EXTENSIONS.contains(extension)) {
      return FileUsageType.BACKUP;
    } else {
      throw new IllegalArgumentException("허용되지 않는 확장자: " + extension);
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
      file.transferTo(destFile);
      return destFile;
    } catch (IOException e) {
      throw new RuntimeException("파일 저장 중 오류 발생", e);
    }
  }
}
