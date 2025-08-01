package com.team1.hrbank.domain.changelog.service;

import com.team1.hrbank.domain.changelog.dto.data.ChangeLogDto;
import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import com.team1.hrbank.domain.changelog.entity.ChangeLogType;
import com.team1.hrbank.domain.changelog.mapper.ChangeLogDiffMapper;
import com.team1.hrbank.domain.changelog.repository.ChangeLogDiffRepository;
import com.team1.hrbank.domain.changelog.repository.ChangeLogRepository;
import com.team1.hrbank.domain.changelog.repository.helper.ChangeLogSpecification;
import com.team1.hrbank.domain.employee.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private final ChangeLogDiffRepository changeLogDiffRepository;
    private final ChangeLogDiffMapper changeLogDiffMapper;

    public List<ChangeLogDto> findAll(ChangeLogSearchRequest request) {
        Specification<ChangeLog> spec = ChangeLogSpecification.changeLogSpecification(request); //필터링 기준 생성
        Sort sort = switch (request.sortKey()){
            case IP_ADDRESS -> Sort.by(Sort.Direction.DESC, "ipAddress");
            case CREATED_AT -> Sort.by(Sort.Direction.DESC, "createdAt");
        };

        if(request.lastId() != null){
            spec = spec.and((root, query, cb) ->
                    cb.lessThan(root.get("id"), request.lastId())); // 페이징
        }

        int size = 20; //한번에 불러오는 데이터 갯수
        PageRequest pageable = PageRequest.of(0, size, sort);
        List<ChangeLog> changeLogs = changeLogRepository.findAll(spec, pageable).getContent();

        return changeLogs.stream()
                .
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
