package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.file.dto.StoredFileInfo;
import com.team1.hrbank.domain.file.exception.FileErrorCode;
import com.team1.hrbank.domain.file.exception.FileException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

@Component
@Slf4j
public class FileStorageManager {

  private static final List<String> ALLOWED_PROFILE_EXTENSIONS = List.of("png", "jpg", "jpeg");
  private static final List<String> ALLOWED_BACKUP_EXTENSIONS = List.of("csv");
  private static final String PROFILE_PATH = "uploads/profile";
  private static final String BACKUP_PATH = "uploads/backup";
  private static final String ERROR_LOG_PATH = "uploads/backup/error-log";
  private final View error;

  public FileStorageManager(View error) {
    this.error = error;
  }

  public StoredFileInfo uploadProfileImage(Long employeeId, MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    validateOriginalFileName(originalFilename);

    String extension = extractExtension(originalFilename);
    validateAllowedExtension(ALLOWED_PROFILE_EXTENSIONS, extension);

    String rootPath = System.getProperty("user.dir");
    File uploadDir = new File(rootPath, PROFILE_PATH);
    ensureDirectoryExists(uploadDir);

    String savedName = "profile_" + employeeId + "_" + UUID.randomUUID() + "." + extension;
    try {
      File savedFile = saveFileToDirectory(file, uploadDir, savedName);

      return new StoredFileInfo(savedFile, extension);
    } catch (Exception e) {
      throw new FileException(FileErrorCode.FILE_UPLOAD_FAILED);
    }

  }

  public void deleteFile(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return;
    }

    File file = new File(filePath);

    if (file.exists()) {
      boolean deleted = file.delete();
      if (!deleted) {
        throw new FileException(FileErrorCode.FILE_DELETE_FAILED);
      }
    }
  }

  public StoredFileInfo generateBackupFile(Long backupId, List<Employee> employees) {
    String backupContent = "ID,직원번호,이름,이메일,부서,직급,입사일,상태";

    String extension = "csv";
    validateAllowedExtension(ALLOWED_BACKUP_EXTENSIONS, extension);

    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String uniqueId = UUID.randomUUID().toString().substring(0, 8);

    String savedName = String.format("backup_%s_%s_%s.%s", String.valueOf(backupId), timestamp,
        uniqueId, extension);

    String rootPath = System.getProperty("user.dir");
    File backupDir = new File(rootPath, BACKUP_PATH);
    ensureDirectoryExists(backupDir);

    File destFile = writeCsvFile(backupDir, savedName, employees);

    return new StoredFileInfo(destFile, extension);
  }

  public StoredFileInfo generateErrorLogFile(Long backupId, String errorLogContent) {
    String extension = "log";

    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String uniqueId = UUID.randomUUID().toString().substring(0, 8);

    String savedName = String.format("backup_error%s_%s_%s.%s", String.valueOf(backupId), timestamp,
        uniqueId, extension);

    String rootPath = System.getProperty("user.dir");
    File backupDir = new File(rootPath, ERROR_LOG_PATH);
    ensureDirectoryExists(backupDir);

    File destFile = writeLogFile(backupDir, savedName, errorLogContent);

    return new StoredFileInfo(destFile, extension);
  }

  public Resource downloadFile(String filePath) {
    try {
      File file = new File(filePath);
      if (!file.exists()) {
        log.error("파일 다운로드 실패: 존재하지 않는 파일 - {}", filePath);
        throw new FileException(FileErrorCode.FILE_NOT_FOUND);
      }

      return new FileSystemResource(file);
    } catch (Exception e) {
      log.error("파일 다운로드 중 예외 발생: {}", e.getMessage(), e);
      throw new FileException(FileErrorCode.FILE_DOWNLOAD_FAILED);
    }

  }

  private void validateOriginalFileName(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      log.error("유효하지 않은 파일명: {}", fileName);
      throw new FileException(FileErrorCode.INVALID_FILE_NAME);
    }
  }

  private String extractExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
  }

  private void validateAllowedExtension(List<String> allowedExtension, String extension) {
    if (!allowedExtension.contains(extension)) {
      log.error("지원하지 않는 확장자입니다: 요청한 확장자 = {}, 허용 목록 = {}", extension, allowedExtension);
      throw new FileException(FileErrorCode.UNSUPPORTED_EXTENSION);
    }
  }

  private void ensureDirectoryExists(File dir) {
    if (!dir.exists() && !dir.mkdirs()) {
      throw new FileException(FileErrorCode.DIRECTORY_CREATION_FAILED);
    }
  }

  private File saveFileToDirectory(MultipartFile file, File dir, String savedName) {
    try {
      File destFile = new File(dir, savedName);
      file.transferTo(destFile);
      return destFile;
    } catch (IOException e) {
      log.error("파일 저장 실패: dir = {}, savedName = {}, file = {}", dir, savedName, file);
      throw new FileException(FileErrorCode.FILE_SAVE_FAILED);
    }
  }

  private File writeCsvFile(File dir, String savedName, List<Employee> employees) {
    File destFile = new File(dir, savedName);
    File tempFile = new File(dir, "temp_" + System.currentTimeMillis() + "_");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
      for (Employee employee : employees) {
        writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s",
            employee.getId(),
            employee.getEmployeeNumber(),
            employee.getName(), employee.getEmail(),
            employee.getDepartment().getName(),
            employee.getPosition(),
            employee.getHireDate().toString(),
            employee.getStatus().name()));
        writer.newLine();
      }

      writer.flush();

      // 성공 시에만 최종 파일명으로 변경
      if (!tempFile.renameTo(destFile)) {
        log.error("파일 이름 변경 실패: tempFile = {}, destFile = {}", tempFile, destFile);
        throw new FileException(FileErrorCode.FILE_RENAME_FAILED);
      }

      return destFile;
    } catch (IOException e) {
      log.error("CSV 파일 저장 중 IOException 발생: {}", e.getMessage(), e);
      throw new FileException(FileErrorCode.CSV_WRITE_FAILED);
    }
  }

  private File writeLogFile(File dir, String savedName, String errorLogContent) {
    File destFile = new File(dir, savedName);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {
      writer.write(errorLogContent);
      return destFile;
    } catch (IOException e) {
      log.error("에러 로그 저장 실패: file = {}, errorLogContent = {}", destFile, errorLogContent);
      throw new FileException(FileErrorCode.LOG_WRITE_FAILED);
    }
  }
}
