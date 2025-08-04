package com.team1.hrbank.domain.backup.service;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.dto.response.CursorPageResponseBackupDto;
import com.team1.hrbank.domain.backup.entity.Backup;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import com.team1.hrbank.domain.backup.exception.BackupException;
import com.team1.hrbank.domain.backup.mapper.BackupMapper;
import com.team1.hrbank.domain.backup.repository.BackupRepository;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.repository.ChangeLogRepository;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.service.FileMetadataService;
import com.team1.hrbank.global.constant.BakcupErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BackupService {

  private final BackupRepository backupRepository;
  private final ChangeLogRepository changeLogRepository;
  private final EmployeeRepository employeeRepository;

  private final FileMetadataService fileMetaDataService;

  private final BackupMapper backMapper;

  public BackupDto createBackup(String workerIp) {
    if (shouldSkipBackup()) {
      return skipBackup(workerIp);
    }

    Backup backup = Backup.builder()
        .worker(workerIp)
        .status(BackupStatus.IN_PROGRESS)
        .build();

    backupRepository.save(backup);

    try {
      List<Employee> employees = employeeRepository.findAll();

      FileMetadata fileMetaData = fileMetaDataService.generateBackupFile(backup.getId(),
          employees);
      return saveBackup(backup, fileMetaData, workerIp, BackupStatus.COMPLETED);

    } catch (Exception e) {
      fileMetaDataService.generateErrorLogFile(backup.getId(), e.getMessage());
      throw new BackupException(BakcupErrorCode.BACKUP_FAILED);
    }
  }

  private boolean shouldSkipBackup() {
    Optional<ChangeLog> recentChangeLog = changeLogRepository.findFirstByUpdatedAtDesc();
    if (recentChangeLog.isEmpty()) {  //수정 내역 없으면 스킵
      return true;
    }
    LocalDateTime recentChangeLogTime = recentChangeLog.get().getUpdatedAt();

    Optional<Backup> recentBackup = backupRepository.findFirstOrderByEndedAtDesc();
    if (recentBackup.isEmpty()) { // 첫 백업 수행해야함
      return false;
    }
    LocalDateTime recentBackupTime = recentBackup.get().getEndedAt();

    return recentChangeLogTime.isBefore(recentBackupTime);
  }

  private BackupDto skipBackup(String workerIp) {
    Backup backup = new Backup(workerIp, BackupStatus.SKIPPED, LocalDateTime.now(), null);
    backupRepository.save(backup);
    return backMapper.toDto(backup);
  }

  private BackupDto saveBackup(Backup backup, FileMetadata fileMetadata, String workerIp,
      BackupStatus status) {
    backup.setFileMetadata(fileMetadata);
    backup.setStatus(status);
    backup.setEndedAt(LocalDateTime.now());
    backup.setWorker(workerIp);
    backupRepository.save(backup);
    return backMapper.toDto(backup);
  }

  @Transactional(readOnly = true)
  public CursorPageResponseBackupDto findAll(
      String worker,
      BackupStatus status,
      LocalDateTime startedAtFrom,
      LocalDateTime startedAtTo,
      Long idAfter,
      String cursor,          // startedAt이나 endedAt의 시간 값
      Integer size,
      String sortField,      // "startedAt or endedAt"
      String sortDirection   // "asc" or "desc"
  ) {

    Specification<Backup> spec = createSpec(worker, status, startedAtFrom, startedAtTo, idAfter);
    Pageable pageable = createPageable(size, sortField, sortDirection);

    Page<Backup> backupPage;
    if (spec == null) {
      backupPage = backupRepository.findAll(pageable);
    } else {
      backupPage = backupRepository.findAll(spec, pageable);
    }

    Long nextIdAfter = null;
    String nextCursor = null;

    List<BackupDto> backupDtos = backupPage.getContent().stream()
        .map(backMapper::toDto)
        .toList();

    if (!backupDtos.isEmpty()) {
      nextIdAfter = backupDtos.get(backupDtos.size() - 1).id();
      nextCursor = sortField.equals("startedAt") ?
          backupDtos.get(backupDtos.size() - 1).startedAt().toString() :
          backupDtos.get(backupDtos.size() - 1).endedAt().toString();
    }

    return new CursorPageResponseBackupDto(
        backupDtos,
        nextCursor,
        nextIdAfter,
        backupPage.getSize(),
        backupPage.getTotalElements(),
        backupPage.hasNext()
    );
  }

  private Specification<Backup> createSpec(String worker, BackupStatus status,
      LocalDateTime startedAtFrom, LocalDateTime startedAtTo, Long idAfter) {
    Specification<Backup> spec = Specification.where(null);

    if (worker != null && !worker.isEmpty()) {
      spec = spec.and((root, query, cb) ->
          cb.like(root.get("worker"), "%" + worker + "%"));
    }

    if (status != null) {
      spec = spec.and((root, query, cb) ->
          cb.equal(root.get("status"), status));
    }

    if (startedAtFrom != null) {
      spec = spec.and((root, query, cb) ->
          cb.greaterThanOrEqualTo(root.get("startedAt"), startedAtFrom));
    }

    if (startedAtTo != null) {
      spec = spec.and((root, query, cb) ->
          cb.lessThanOrEqualTo(root.get("startedAt"), startedAtTo));
    }

    if (idAfter != null) {
      spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("id"), idAfter));
    }
    return spec;
  }

  private Pageable createPageable(Integer size, String sortField, String sortDirection) {
    Sort.Direction direction =
        "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort sort = Sort.by(direction, sortField);
    return PageRequest.of(0, size, sort);
  }

  @Transactional(readOnly = true)
  public BackupDto findLatest(String status) {
    Backup backup = backupRepository.findFirstByStatusOrderByCreatedAtDesc(BackupStatus.valueOf(status))
        .orElseThrow(() -> new BackupException(BakcupErrorCode.RECENT_BACKUP_NOT_EXIST));
    return backMapper.toDto(backup);
  }
}
