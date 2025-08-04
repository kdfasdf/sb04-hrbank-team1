package com.team1.hrbank.domain.changelog.controller;

import com.team1.hrbank.domain.changelog.dto.response.ChangeLogDiffResponse;
import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogSearchResponse;
import com.team1.hrbank.domain.changelog.service.ChangeLogDiffService;
import com.team1.hrbank.domain.changelog.service.ChangeLogService;
import com.team1.hrbank.global.api.ChangeLogApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "직원 정보 수정 이력 관리", description = "직원 정보 수정 이력 관리 API")
@RequestMapping("/api/change-logs")
public class ChangeLogController implements ChangeLogApi {

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

