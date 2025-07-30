package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.file.dto.FileMetadataDto;
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
public class BasicFileMetadataService implements FileMetadataService {

  private final FileMetadataRepository fileMetadataRepository;
  private final FileMetadataMapper fileMetadataMapper;

  private static final List<String> ALLOWED_EXTENSIONS = List.of("png", "jpg", "jpeg");
  private static final String PROFILE_PATH = "uploads/profile";

  @Override
  @Transactional
  public FileMetadataDto uploadProfile(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null || !originalFilename.contains(".")) {
      throw new IllegalArgumentException("파일 이름이 올바르지 않음");
    }

    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)
        .toLowerCase();
    if (!ALLOWED_EXTENSIONS.contains(extension)) {
      throw new IllegalArgumentException("png, jpg, jpeg만 가능");
    }

    File uploadDir = new File(PROFILE_PATH);
    if (!uploadDir.exists() && !uploadDir.mkdirs()) {
      throw new RuntimeException("디렉토리 생성 실패");
    }

    String uniqueFileName = UUID.randomUUID() + "." + extension;
    File destFile = new File(uploadDir, uniqueFileName);
    try {
      file.transferTo(destFile); // 실제 파일 저장
    } catch (IOException e) {
      throw new RuntimeException("파일 저장 중 오류 발생", e);
    }

    FileMetadata metadata = FileMetadata.builder()
        .fileName(file.getOriginalFilename())
        .fileType(FileType.valueOf(extension.toUpperCase()))
        .fileUsageType(FileUsageType.PROFILE)
        .fileSize(file.getSize())
        .filePath(destFile.getAbsolutePath())
        .build();

    FileMetadata savedProfileImage = fileMetadataRepository.save(metadata);

    return fileMetadataMapper.mapToDto(savedProfileImage);
  }
}
