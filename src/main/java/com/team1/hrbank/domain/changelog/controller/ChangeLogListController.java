package com.team1.hrbank.domain.changelog.controller;

import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogSearchResponse;
import com.team1.hrbank.domain.changelog.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogListController {

    private final ChangeLogService changeLogService;

    @GetMapping
    public ResponseEntity<ChangeLogSearchResponse> getChangeLogs(
            @ModelAttribute ChangeLogSearchRequest changeLogSearchRequest
    ) {
        ChangeLogSearchResponse response = changeLogService.findAll(changeLogSearchRequest);
        return ResponseEntity.ok(response);
    }
}

