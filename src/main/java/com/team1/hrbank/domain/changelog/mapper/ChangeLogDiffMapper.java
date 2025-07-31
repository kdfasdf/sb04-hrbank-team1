package com.team1.hrbank.domain.changelog.mapper;

import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.entity.ChangeLogDiff;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ChangeLogDiffMapper {

    String EMPTY = "-";

    default List<ChangeLogDiff> fromCreate(ChangeLog log, Employee after) {
        return List.of(
                new ChangeLogDiff(log, "입사일", EMPTY, after.getHireDate().toString()),
                new ChangeLogDiff(log, "이름", EMPTY, after.getName()),
                new ChangeLogDiff(log, "직함", EMPTY, after.getPosition()),
                new ChangeLogDiff(log, "부서명", EMPTY, after.getDepartment().getName()),
                new ChangeLogDiff(log, "이메일", EMPTY, after.getEmail()),
                new ChangeLogDiff(log, "사번", EMPTY, after.getEmployeeNumber()),
                new ChangeLogDiff(log, "상태", EMPTY, after.getStatus().name())
        );
    }

    default List<ChangeLogDiff> fromDelete(ChangeLog log, Employee before) {
        return List.of(
                new ChangeLogDiff(log, "입사일", before.getHireDate().toString(), EMPTY),
                new ChangeLogDiff(log, "이름", before.getName(), EMPTY),
                new ChangeLogDiff(log, "직함", before.getPosition(), EMPTY),
                new ChangeLogDiff(log, "부서명", before.getDepartment().getName(), EMPTY),
                new ChangeLogDiff(log, "이메일", before.getEmail(), EMPTY),
                new ChangeLogDiff(log, "상태", before.getStatus().name(), EMPTY)
        );
    }

    default List<ChangeLogDiff> fromUpdate(ChangeLog log, Employee before, Employee after) {
        List<ChangeLogDiff> diffs = new ArrayList<>();

        if (!Objects.equals(before.getName(), after.getName())) {
            diffs.add(new ChangeLogDiff(log, "이름", before.getName(), after.getName()));
        }
        if (!Objects.equals(before.getEmail(), after.getEmail())) {
            diffs.add(new ChangeLogDiff(log, "이메일", before.getEmail(), after.getEmail()));
        }
        if (!Objects.equals(before.getPosition(), after.getPosition())) {
            diffs.add(new ChangeLogDiff(log, "직함", before.getPosition(), after.getPosition()));
        }
        if (!Objects.equals(before.getHireDate(), after.getHireDate())) {
            diffs.add(new ChangeLogDiff(log, "입사일", before.getHireDate().toString(), after.getHireDate().toString()));
        }
        if (!Objects.equals(before.getDepartment().getName(), after.getDepartment().getName())) {
            diffs.add(new ChangeLogDiff(log, "부서명", before.getDepartment().getName(), after.getDepartment().getName()));
        }
        if (!Objects.equals(before.getStatus(), after.getStatus())) {
            diffs.add(new ChangeLogDiff(log, "상태", before.getStatus().name(), after.getStatus().name()));
        }

        return diffs;
    }
}
