package com.team1.hrbank.domain.backup.service;

import com.team1.hrbank.domain.backup.entity.Backup;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import com.team1.hrbank.domain.backup.repository.BackupRepository;
import com.team1.hrbank.domain.backup.service.BackupService.FileService;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.domain.employee.repository.EmployeeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackupService {

  private final BackupRepository backupRepository;
  private final ChangeLogRepository changeLogRepository;
  private final EmployeeRepository employeeRepository;

  private final FileService fileMetaDataService;

  public BackupDto createBackup(String workerIp) {
    if (shouldSkipBackup()) {
      return skipBackup(workerIp);
    }

    Backup backup = new Backup(workerIp, BackupStatus.IN_PROGRESS, LocalDateTime.now(), null);
    backupRepository.save(backup);

    try {
      List<Employee> employees = employeeRepository.findAll();
      String employeeCsvFormat = makeEmployeeCsvFile(employees);

      FileMetaData fileMetaData =fileMetaDataService.createBackupFile(backup.getId(), employeeCsvFormat);
      backup.setMetadata(fileMetaData);

      backup.setStatus(BackupStatus.COMPLETED);
      backup.setEndedAt(LocalDateTime.now());
      backupRepository.save(backup);
      return new BackupDto(backup.getId(), workerIp, backup.getCreatedAt(), backup.getEndedAt(), BackupStatus.COMPLETED, fileMetaData.getId());
    } catch (Exception e) {
      backup.setStatus(BackupStatus.FAIL);
      backup.setEndedAt(LocalDateTime.now());
      backupRepository.save(backup);
      FileMetaData fileMetadata = fileMetaDataService.createErrorLogFile(backup.getId(), e.getMessage());
      return new BackupDto(backup.getId(), workerIp, backup.getCreatedAt(), backup.getEndedAt(), BackupStatus.FAIL, fileMetaData.getId();
    }

    return new BackupDto(0L, workerIp, null, null, BackupStatus.SKIPPED, null);
  }

  private boolean shouldSkipBackup() {
    Optional<ChangeLog> recentChangeLog = changeLogRepository.findRecentChangeLog();
    if(recentChangeLog.isEmpty()) {  //수정 내역 없으면 스킵
      return true;
    }
    LocalDateTime recentChangeLogTime = recentChangeLog.get().getUpdatedAt();

    Optional<Backup> recentBackup = backupRepository.findRecentBackup();
    if(recentBackup.isEmpty()) { // 첫 백업 수행해야함
      return false;
    }
    LocalDateTime recentBackupTime = recentBackup.get().getEndedAt();

    return recentChangeLogTime.isAfter(recentBackupTime);
  }

  private BackupDto skipBackup(String workerIp) {
    Backup backup = new Backup(workerIp, BackupStatus.SKIPPED, LocalDateTime.now(), null);
    backupRepository.save(backup);
    return new BackupDto(backup.getId(), workerIp, backup.getCreatedAt(), backup.getEndedAt(), BackupStatus.SKIPPED, null);
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
}
