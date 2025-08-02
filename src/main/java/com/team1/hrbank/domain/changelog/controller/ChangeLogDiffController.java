package com.team1.hrbank.domain.changelog.controller;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDiffDto;
import com.team1.hrbank.domain.changelog.service.ChangeLogDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogDiffController {

    private final ChangeLogDiffService changeLogDiffService;

    @GetMapping("/{changeLogId}/diffs")
    public ResponseEntity<List<ChangeLogDiffDto>> getDiffs(@PathVariable Long changeLogId) {
        List<ChangeLogDiffDto> diffs = changeLogDiffService.findAllByChangeLogId(changeLogId);
        return ResponseEntity.ok(diffs);
    }
}
