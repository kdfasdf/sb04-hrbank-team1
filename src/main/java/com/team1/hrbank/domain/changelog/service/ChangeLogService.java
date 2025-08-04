package com.team1.hrbank.domain.changelog.service;

import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogSearchResponse;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import com.team1.hrbank.domain.changelog.entity.ChangeLogType;
import com.team1.hrbank.domain.changelog.exception.ChangeLogException;
import com.team1.hrbank.domain.changelog.mapper.ChangeLogDiffMapper;
import com.team1.hrbank.domain.changelog.mapper.ChangeLogMapper;
import com.team1.hrbank.domain.changelog.repository.ChangeLogDiffRepository;
import com.team1.hrbank.domain.changelog.repository.ChangeLogRepository;
import com.team1.hrbank.domain.employee.entity.Employee;
import com.team1.hrbank.global.constant.ChangeLogErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeLogService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private final ChangeLogRepository changeLogRepository;
    private final ChangeLogDiffRepository changeLogDiffRepository;
    private final ChangeLogDiffMapper changeLogDiffMapper;
    private final ChangeLogMapper changeLogMapper;

    @Transactional(readOnly = true)
    public ChangeLogSearchResponse findAll(ChangeLogSearchRequest request) {
        int limit = DEFAULT_PAGE_SIZE + 1;

        List<ChangeLog> changeLogs = changeLogRepository.findAllByCondition(
                request.employeeNumber(),
                request.memo(),
                request.ipAddress(),
                request.type() != null ? request.type().name() : null,
                request.from(),
                request.to(),
                request.lastId(),
                getDirection(request.sortKey()),
                request.sortKey().name(),
                limit
        );

        boolean hasNext = changeLogs.size() > DEFAULT_PAGE_SIZE;
        Long nextId = hasNext ? changeLogs.get(DEFAULT_PAGE_SIZE).getId() : null;
        String nextCursor = nextId != null
                ? Base64.getEncoder().encodeToString(("{\"id\":" + nextId + "}").getBytes(StandardCharsets.UTF_8))
                : null;

        if (hasNext) {
            changeLogs = changeLogs.subList(0, DEFAULT_PAGE_SIZE);
        }

        long totalCount = changeLogRepository.countByCondition(
                request.employeeNumber(),
                request.memo(),
                request.ipAddress(),
                request.type() != null ? request.type().name() : null,
                request.from(),
                request.to()
        );

        return new ChangeLogSearchResponse(
                changeLogs.stream().map(changeLogMapper::toDto).toList(),
                nextCursor,
                nextId,
                DEFAULT_PAGE_SIZE,
                totalCount,
                hasNext
        );
    }

    private String getDirection(ChangeLogSearchRequest.SortKey sortKey) {
        if (sortKey == null) {
            throw new ChangeLogException(ChangeLogErrorCode.INVALID_SORT_KEY);
        }

        return switch (sortKey) {
            case CREATED_AT_ASC, IP_ADDRESS_ASC -> "ASC";
            case CREATED_AT_DESC, IP_ADDRESS_DESC -> "DESC";
            default -> throw new ChangeLogException(ChangeLogErrorCode.INVALID_SORT_KEY);
        };
    }

    @Transactional(readOnly = true)
    public Long countByPeriod(LocalDateTime fromTemp, LocalDateTime toTemp) {
        LocalDateTime from = fromTemp == null ? LocalDateTime.now().minusDays(7) : fromTemp;
        LocalDateTime to = toTemp == null ? LocalDateTime.now() : toTemp;
        if (from.isAfter(to)) {
            throw new ChangeLogException(ChangeLogErrorCode.INVALID_DATE_RANGE);
        }
        return changeLogRepository.countByCreatedAtBetween(from, to);
    }

    public void recordCreateLog(Employee employee, String memo, String ipAddress) {
        if (employee == null || employee.getEmployeeNumber() == null) {
            throw new ChangeLogException(ChangeLogErrorCode.EMPLOYEE_NUMBER_REQUIRED);
        }

        ChangeLog log = new ChangeLog(
                employee.getEmployeeNumber(),
                ChangeLogType.CREATED,
                memo,
                ipAddress
        );
        changeLogRepository.save(log);

        List<ChangeLogDiff> diffs = changeLogDiffMapper.fromCreate(log, employee);
        changeLogDiffRepository.saveAll(diffs);
    }

    public void recordUpdateLog(Employee before, Employee after, String memo, String ipAddress) {
        if (before == null || after == null) {
            throw new ChangeLogException(ChangeLogErrorCode.EMPLOYEE_BEFORE_AFTER_REQUIRED);
        }

        ChangeLog log = new ChangeLog(
                before.getEmployeeNumber(),
                ChangeLogType.UPDATED,
                memo,
                ipAddress
        );
        changeLogRepository.save(log);

        List<ChangeLogDiff> diffs = changeLogDiffMapper.fromUpdate(log, before, after);
        if (!diffs.isEmpty()) {
            changeLogDiffRepository.saveAll(diffs);
        }
    }

    public void recordDeleteLog(Employee employee, String memo, String ipAddress) {
        if (employee == null || employee.getEmployeeNumber() == null) {
            throw new ChangeLogException(ChangeLogErrorCode.EMPLOYEE_NUMBER_REQUIRED);
        }

        ChangeLog log = new ChangeLog(
                employee.getEmployeeNumber(),
                ChangeLogType.DELETED,
                memo,
                ipAddress
        );
        changeLogRepository.save(log);

        List<ChangeLogDiff> diffs = changeLogDiffMapper.fromDelete(log, employee);
        changeLogDiffRepository.saveAll(diffs);
    }
}
