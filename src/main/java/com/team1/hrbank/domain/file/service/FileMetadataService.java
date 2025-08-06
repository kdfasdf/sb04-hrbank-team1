package com.team1.hrbank.domain.file.service;

import com.team1.hrbank.domain.backup.repository.BackupRepository;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.exception.EmployeeException;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.file.dto.StoredFileInfo;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.entity.FileType;
import com.team1.hrbank.domain.file.exception.FileException;
import com.team1.hrbank.domain.file.repository.FileMetadataRepository;
import com.team1.hrbank.global.constant.EmployeeErrorCode;
import com.team1.hrbank.global.constant.FileErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

  private final FileMetadataRepository fileMetadataRepository;
  private final FileStorageManager fileStorageManager;
  private final EmployeeRepository employeeRepository;
  private final BackupRepository backupRepository;

  @Transactional
  public FileMetadata uploadProfileImage(Long employeeId, MultipartFile file) {
    Employee employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new EmployeeException(EmployeeErrorCode.EMPLOYEE_NOT_FOUND));

    // 기존 이미지 삭제
    FileMetadata oldMeta = employee.getFileMetadata();
    if (oldMeta != null) {
      fileStorageManager.deleteFile(oldMeta.getFilePath()); // 물리적 파일 삭제
      fileMetadataRepository.delete(oldMeta); // DB 삭제
    }

    StoredFileInfo stored = fileStorageManager.uploadProfileImage(employeeId, file);
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
  public FileMetadata generateBackupFile(Long backupId, List<Employee> employees) {

    StoredFileInfo stored = fileStorageManager.generateBackupFile(backupId, employees);
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

  @Transactional
  public FileMetadata generateErrorLogFile(Long backupId, String errorLogContent) {
    StoredFileInfo stored = fileStorageManager.generateErrorLogFile(backupId, errorLogContent);
    FileMetadata metadata = FileMetadata.builder()
        .originalName(stored.file().getName())
        .savedName(stored.file().getName())
        .extension(stored.extension())
        .fileType(FileType.ERROR_LOG)
        .fileSize(stored.file().length())
        .filePath(String.valueOf(stored.file().getAbsoluteFile()))
        .build();

    return fileMetadataRepository.save(metadata);
  }

  @Transactional(readOnly = true)
  public Resource downloadFile(Long id) {
    FileMetadata metadata = fileMetadataRepository.findById(id)
        .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

    return fileStorageManager.downloadFile(metadata.getFilePath());
  }

  @Transactional(readOnly = true)
  public FileMetadata getFileMetadata(Long id) {
    return fileMetadataRepository.findById(id)
        .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
  }
}
