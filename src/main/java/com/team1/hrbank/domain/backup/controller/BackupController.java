package com.team1.hrbank.domain.backup.controller;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.service.BackupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BackupController {

  private final BackupService backupService;

  @PostMapping("/backups")
  ResponseEntity<BackupDto> createBackup(HttpServletRequest request) {

    String workerIp = request.getHeader("X-FORWARDED-FOR");
    if (workerIp == null || workerIp.isEmpty() || "unknown".equalsIgnoreCase(workerIp)) {
      workerIp = request.getRemoteAddr();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(backupService.createBackup(workerIp));
  }

}
