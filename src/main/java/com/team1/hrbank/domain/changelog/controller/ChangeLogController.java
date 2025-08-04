package com.team1.hrbank.domain.changelog.controller;

import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogDiffResponse;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogSearchResponse;
import com.team1.hrbank.domain.changelog.service.ChangeLogDiffService;
import com.team1.hrbank.domain.changelog.service.ChangeLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogController {

  private final ChangeLogService changeLogService;
  private final ChangeLogDiffService changeLogDiffService;

  @GetMapping
  public ResponseEntity<ChangeLogSearchResponse> getChangeLogs(
      @ModelAttribute ChangeLogSearchRequest changeLogSearchRequest
  ) {
    ChangeLogSearchResponse response = changeLogService.findAll(changeLogSearchRequest);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{changeLogId}/diffs")
  public ResponseEntity<List<ChangeLogDiffResponse>> getDiffs(@PathVariable Long changeLogId) {
    List<ChangeLogDiffResponse> diffs = changeLogDiffService.findAllByChangeLogId(changeLogId);
    return ResponseEntity.ok(diffs);
  }

  @GetMapping("/count")
  public ResponseEntity<Long> getChangeLogsCount(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
  ) {
    return ResponseEntity.ok(changeLogService.countByPeriod(fromDate, toDate));
  }
}

