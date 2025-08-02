package com.team1.hrbank.domain.changelog.controller;

import com.team1.hrbank.domain.changelog.dto.response.ChangeLogDiffResponse;
import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogCountResponse;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogSearchResponse;
import com.team1.hrbank.domain.changelog.service.ChangeLogDiffService;
import com.team1.hrbank.domain.changelog.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<ChangeLogCountResponse> getChangeLogsCount(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ){
        ChangeLogCountResponse count = changeLogService.countByPeriod(fromDate, toDate);
        return ResponseEntity.ok(count);
    }
}

