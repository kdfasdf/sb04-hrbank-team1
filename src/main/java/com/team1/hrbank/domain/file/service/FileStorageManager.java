package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.file.dto.StoredFileInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageManager {

  private static final List<String> ALLOWED_PROFILE_EXTENSIONS = List.of("png", "jpg", "jpeg");
  private static final List<String> ALLOWED_BACKUP_EXTENSIONS = List.of("csv");
  private static final String PROFILE_PATH = "uploads/profile";
  private static final String BACKUP_PATH = "uploads/backup";
  private static final String ERROR_LOG_PATH = "uploads/backup/error-log";

  public StoredFileInfo uploadProfileImage(Long employeeId, MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    validateOriginalFileName(originalFilename);

    String extension = extractExtension(originalFilename);
    validateAllowedExtension(ALLOWED_PROFILE_EXTENSIONS, extension);

    String rootPath = System.getProperty("user.dir");
    File uploadDir = new File(rootPath, PROFILE_PATH);
    ensureDirectoryExists(uploadDir);

    String savedName = "profile_" + employeeId + "_" + UUID.randomUUID() + "." + extension;
    File savedFile = saveFileToDirectory(file, uploadDir, savedName);

    return new StoredFileInfo(savedFile, extension);
  }

  public void deleteFile(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return;
    }

    File file = new File(filePath);

    if (file.exists()) {
      boolean deleted = file.delete();
      if (!deleted) {
        throw new RuntimeException("파일 삭제 실패: " + filePath);
      }
    }
  }

  public StoredFileInfo generateBackupFile(Long backupId, String backupContent) {
    if (backupContent == null || backupContent.trim().isEmpty()) {
      backupContent = "ID,직원번호,이름,이메일,부서,직급,입사일,상태";
    }
    String extension = "csv";
    validateAllowedExtension(ALLOWED_BACKUP_EXTENSIONS, extension);

    String savedName = "backup_" + backupId + "_" + UUID.randomUUID() + "." + extension;

    String rootPath = System.getProperty("user.dir");
    File backupDir = new File(rootPath, BACKUP_PATH);
    ensureDirectoryExists(backupDir);

    File destFile = writeCsvFile(backupDir, savedName, backupContent);

    return new StoredFileInfo(destFile, extension);
  }

  public StoredFileInfo generateErrorLogFile(Long backupId, String errorLogContent) {
    String extension = "log";

    String savedName = "error_" + backupId + "_" + UUID.randomUUID() + ".log";
    String rootPath = System.getProperty("user.dir");
    File backupDir = new File(rootPath, ERROR_LOG_PATH);
    ensureDirectoryExists(backupDir);

    File destFile = writeLogFile(backupDir, savedName, errorLogContent);

    return new StoredFileInfo(destFile, extension);
  }

  public Resource downloadFile(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      throw new RuntimeException("파일 없음: " + filePath);
    }

    return new FileSystemResource(file);
  }

  private void validateOriginalFileName(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      throw new IllegalArgumentException("올바르지 않은 파일명");
    }
  }

  private String extractExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
  }

  private void validateAllowedExtension(List<String> allowedExtension, String extension) {
    if (!allowedExtension.contains(extension)) {
      throw new IllegalArgumentException("허용되지 않는 확장자, 현재 확장자: " + extension);
    }
  }

  private void ensureDirectoryExists(File dir) {
    if (!dir.exists() && !dir.mkdirs()) {
      throw new RuntimeException("디렉토리 생성 실패");
    }
  }

  private File saveFileToDirectory(MultipartFile file, File dir, String savedName) {
    try {
      File destFile = new File(dir, savedName);
      file.transferTo(destFile);
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

  private File writeLogFile(File dir, String savedName, String errorLogContent) {
    File destFile = new File(dir, savedName);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {
      writer.write(errorLogContent);
      return destFile;
    } catch (IOException e) {
      throw new RuntimeException("log 파일 저장 실패", e);
    }
  }
}
