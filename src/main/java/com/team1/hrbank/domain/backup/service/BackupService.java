package com.team1.hrbank.domain.backup.service;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.entity.Backup;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import com.team1.hrbank.domain.backup.mapper.BackupMapper;
import com.team1.hrbank.domain.backup.repository.BackupRepository;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.repository.ChangeLogRepository;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import com.team1.hrbank.domain.file.entity.FileMetadata;
import com.team1.hrbank.domain.file.service.FileMetadataService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
      String employeeCsvFormat = makeEmployeeCsvFile(employees);

      FileMetadata fileMetaData =fileMetaDataService.generateBackupFile(backup.getId(), employeeCsvFormat);
      return saveBackup(backup, fileMetaData, workerIp, BackupStatus.COMPLETED);

    } catch (Exception e) {
      //Todo 백업중이던 파일 삭제 fileMetaDataService.cancelGenerateBackFile(backup.getId());
      FileMetadata fileMetadata = fileMetaDataService.generateErrorLogFile(backup.getId(), e.getMessage());
      //Todo 커스텀 예외 처리 후 GlobalExceptionHandler에서 에러 응답으로 반환
    }

    backup.setEndedAt(LocalDateTime.now());
    return backMapper.toDto(backup);
  }

  private boolean shouldSkipBackup() {
    Optional<ChangeLog> recentChangeLog = changeLogRepository.findFirstByUpdatedAtDesc();
    if(recentChangeLog.isEmpty()) {  //수정 내역 없으면 스킵
      return true;
    }
    LocalDateTime recentChangeLogTime = recentChangeLog.get().getUpdatedAt();

    Optional<Backup> recentBackup = backupRepository.findFirstByEndedAtDesc();
    if(recentBackup.isEmpty()) { // 첫 백업 수행해야함
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


  // Todo OOM 이슈 발생 가능성 있으니 책임 이관 예정
  private String makeEmployeeCsvFile(List<Employee> employees) {
    String header = "id,employNumber,name,email,position,hireDate,status,departmentId";

    String body = employees.stream()
        .map(employee -> String.format("%s,%s,%s,%s,%s,%s,%s,%s",
            employee.getId(),
            employee.getEmployeeNumber(),
            employee.getName(),
            employee.getEmail(),
            employee.getPosition(),
            employee.getHireDate(),
            employee.getStatus(),
            employee.getDepartment().getId()
        )).collect(Collectors.joining("\n"));

    return header + "\n" +  body;
  }

  private BackupDto saveBackup(Backup backup, FileMetadata fileMetadata, String workerIp, BackupStatus status) {
    backup.setMetadata(fileMetadata);
    backup.setStatus(status);
    backup.setEndedAt(LocalDateTime.now());
    backup.setWorker(workerIp);
    backupRepository.save(backup);
    return backMapper.toDto(backup);
  }
}
