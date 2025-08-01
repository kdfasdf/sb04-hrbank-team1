package com.team1.hrbank.domain.changelog.service;

import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.dto.response.ChangeLogPageResponse;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import com.team1.hrbank.domain.changelog.entity.ChangeLogType;
import com.team1.hrbank.domain.changelog.mapper.ChangeLogDiffMapper;
import com.team1.hrbank.domain.changelog.mapper.ChangeLogMapper;
import com.team1.hrbank.domain.changelog.repository.ChangeLogDiffRepository;
import com.team1.hrbank.domain.changelog.repository.ChangeLogRepository;
import com.team1.hrbank.domain.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private final ChangeLogDiffRepository changeLogDiffRepository;
    private final ChangeLogDiffMapper changeLogDiffMapper;
    private final ChangeLogMapper changeLogMapper;

    private static final int DEFAULT_PAGE_SIZE = 20; //한번에 불러오는 데이터 갯수

    public ChangeLogPageResponse findAll(ChangeLogSearchRequest request) {
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
        Long nextCursor = hasNext ? changeLogs.get(DEFAULT_PAGE_SIZE).getId() : null;
        if (hasNext) {
            changeLogs = changeLogs.subList(0, DEFAULT_PAGE_SIZE);
        }

        return new ChangeLogPageResponse(
                changeLogs.stream().map(changeLogMapper::toDto).toList(),
                hasNext,
                nextCursor
        );
    }

    private String getDirection(ChangeLogSearchRequest.SortKey key) {
        return switch (key) {
            case CREATED_AT_ASC, IP_ADDRESS_ASC -> "ASC";
            case CREATED_AT_DESC, IP_ADDRESS_DESC -> "DESC";
        };
    }

    //새 직원 객체 생성 후 사용하시면 됩니다
    public void recordCreateLog(Employee employee, String memo, String ipAddress) {
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

    // 업데이트된 객체 생성 후 사용하시면 됩니다. before은 수정 되기 전의 직원 객체
    public void recordUpdateLog(Employee before, Employee after, String memo, String ipAddress) {
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

    //직원 삭제 전에 사용하시면 됩니다.
    public void recordDeleteLog(Employee employee, String memo, String ipAddress) {
        ChangeLog log = new ChangeLog(
                employee.getEmployeeNumber(), // 삭제할 직원
                ChangeLogType.DELETED,
                memo,
                ipAddress
        );
        changeLogRepository.save(log);

        List<ChangeLogDiff> diffs = changeLogDiffMapper.fromDelete(log, employee);
        changeLogDiffRepository.saveAll(diffs);
    }

}
