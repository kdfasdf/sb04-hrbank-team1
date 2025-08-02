package com.team1.hrbank.domain.backup.controller;

import com.team1.hrbank.domain.backup.dto.response.BackupDto;
import com.team1.hrbank.domain.backup.dto.response.CursorPageResponseBackupDto;
import com.team1.hrbank.domain.backup.entity.BackupStatus;
import com.team1.hrbank.domain.backup.service.BackupService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backups")
public class BackupController {

  private final BackupService backupService;

  @PostMapping
  ResponseEntity<BackupDto> createBackup(HttpServletRequest request) {

    String workerIp = request.getHeader("X-FORWARDED-FOR");
    if (workerIp == null || workerIp.isEmpty() || "unknown".equalsIgnoreCase(workerIp)) {
      workerIp = request.getRemoteAddr();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(backupService.createBackup(workerIp));
  }


  @GetMapping
  ResponseEntity<CursorPageResponseBackupDto> findAll(
      @RequestParam(required = false) String worker,
      @RequestParam(required = false) BackupStatus status,
      @RequestParam(required = false) LocalDateTime startedAtFrom,
      @RequestParam(required = false) LocalDateTime startedAtTo,
      @RequestParam(required = false) Long idAfter,
      @RequestParam(required =  false) String cursor,  //zondatatime
      @RequestParam(required = false, defaultValue = "10") Integer size,
      @RequestParam(required = false, defaultValue = "startedAt") String sortField,
      @RequestParam(required = false, defaultValue = "DESC") String sortDirection
  ) {
    CursorPageResponseBackupDto response = backupService.findAll(worker, status, startedAtFrom, startedAtTo, idAfter, cursor, size, sortField, sortDirection);
    return ResponseEntity.ok(response);
  }

   @GetMapping("/lateest")
  public ResponseEntity<BackupDto> getLatest(
      @RequestParam(required = false, defaultValue = "COMPLETED") String status
   ) {
    return ResponseEntity.ok(backupService.findLatest(status));
   }

}
