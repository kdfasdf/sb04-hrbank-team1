package com.team1.hrbank.domain.backup.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BackupScheduler {

  private final BackupService backupService;

  @Scheduled(fixedRateString = "${backup.batch.rate}")
  public void backup() {
    log.info("backup start");
    backupService.createBackup("system");
  }
}
