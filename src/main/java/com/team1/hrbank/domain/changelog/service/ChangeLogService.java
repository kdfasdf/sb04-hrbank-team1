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
        int limit = (request.size() != null ? request.size() : DEFAULT_PAGE_SIZE) + 1;

        String sortField = request.sortField() != null ? request.sortField() : "at";
        String sortDirection = request.sortDirection() != null ? request.sortDirection() : "DESC";

        Long idAfter = request.cursor() != null
                ? extractIdFromCursor(request.cursor())
                : request.idAfter();

        List<ChangeLog> changeLogs;

        if (sortField.equals("at") && sortDirection.equalsIgnoreCase("ASC")) {
            changeLogs = changeLogRepository.findAllOrderByCreatedAtAsc(
                    request.employeeNumber(), request.memo(), request.ipAddress(),
                    request.type() != null ? request.type().name() : null,
                    request.atFrom(), request.atTo(), idAfter, limit
            );
        } else if (sortField.equals("at") && sortDirection.equalsIgnoreCase("DESC")) {
            changeLogs = changeLogRepository.findAllOrderByCreatedAtDesc(
                    request.employeeNumber(), request.memo(), request.ipAddress(),
                    request.type() != null ? request.type().name() : null,
                    request.atFrom(), request.atTo(), idAfter, limit
            );
        } else if (sortField.equals("ipAddress") && sortDirection.equalsIgnoreCase("ASC")) {
            changeLogs = changeLogRepository.findAllOrderByIpAddressAsc(
                    request.employeeNumber(), request.memo(), request.ipAddress(),
                    request.type() != null ? request.type().name() : null,
                    request.atFrom(), request.atTo(), idAfter, limit
            );
        } else if (sortField.equals("ipAddress") && sortDirection.equalsIgnoreCase("DESC")) {
            changeLogs = changeLogRepository.findAllOrderByIpAddressDesc(
                    request.employeeNumber(), request.memo(), request.ipAddress(),
                    request.type() != null ? request.type().name() : null,
                    request.atFrom(), request.atTo(), idAfter, limit
            );
        } else {
            throw new ChangeLogException(ChangeLogErrorCode.INVALID_SORT_PARAMETER);
        }

        boolean hasNext = changeLogs.size() > (limit - 1);
        Long nextId = hasNext ? changeLogs.get(limit - 1).getId() : null;
        String nextCursor = nextId != null
                ? Base64.getEncoder().encodeToString(("{\"id\":" + nextId + "}").getBytes(StandardCharsets.UTF_8))
                : null;

        if (hasNext) {
            changeLogs = changeLogs.subList(0, limit - 1);
        }

        long totalCount = changeLogRepository.countByCondition(
                request.employeeNumber(), request.memo(), request.ipAddress(),
                request.type() != null ? request.type().name() : null,
                request.atFrom(), request.atTo()
        );

        return new ChangeLogSearchResponse(
                changeLogs.stream().map(changeLogMapper::toDto).toList(),
                nextCursor,
                nextId,
                limit - 1,
                totalCount,
                hasNext
        );
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

    private Long extractIdFromCursor(String cursor) {
            String decoded = new String(Base64.getDecoder().decode(cursor), StandardCharsets.UTF_8);
            return Long.valueOf(decoded.replaceAll("[^0-9]", ""));
    }
}
