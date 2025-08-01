package com.team1.hrbank.domain.changelog.repository.helper;

import com.team1.hrbank.domain.changelog.dto.request.ChangeLogSearchRequest;
import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import com.team1.hrbank.domain.changelog.entity.ChangeLogType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;


public class ChangeLogSpecification {

    public static Specification<ChangeLog> changeLogSpecification(ChangeLogSearchRequest condition) {
        return employeeNumberEq(condition.employeeNumber())
                .and(memoContains(condition.memo()))
                .and(ipAddressEq(condition.ipAddress()))
                .and(typeEq(condition.type()))
                .and(createdAtBetween(condition.from(), condition.to()));
    }

    //조건에서 null값을 반환하면 그 조건은 무시되어 필터링 되지 않는다.
    private static Specification<ChangeLog> createdAtBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) {
                return null;
            }
            if (from == null) {
                return cb.lessThanOrEqualTo(root.get("createdAt"), to);
            }
            if (to == null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
            }
            return cb.between(root.get("createdAt"), from, to);
        };
    }

    private static Specification<ChangeLog> employeeNumberEq(String employeeNumber) {
        return ((root, query, cb) ->
                employeeNumber == null ? null : cb.equal(root.get("employeeNumber"), employeeNumber));
    }

    private static Specification<ChangeLog> memoContains(String memo) {
        return ((root, query, cb) ->
               memo == null ? null : cb.like(root.get("memo"), "%" + memo + "%"));
    }

    private static Specification<ChangeLog> ipAddressEq(String ipAddress) {
        return ((root, query, cb) ->
                ipAddress == null ? null : cb.equal(root.get("ipAddress"), "%" + ipAddress + "%"));
    }

    private static Specification<ChangeLog> typeEq(ChangeLogType type) {
        return ((root, query, cb) ->
                type == null ? null : cb.equal(root.get("type"), type));
    }
}
