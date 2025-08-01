package com.team1.hrbank.domain.changelog.repository;

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
        return ((root, query, criteriaBuilder) ->
                employeeNumber == null ? null : criteriaBuilder.equal(root.get("employeeNumber"), employeeNumber));
    }

    private static Specification<ChangeLog> memoContains(String memo) {
        return ((root, query, criteriaBuilder) ->
               memo == null ? null : criteriaBuilder.like(root.get("memo"), "%" + memo + "%"));
    }

    private static Specification<ChangeLog> ipAddressEq(String ipAddress) {
        return ((root, query, criteriaBuilder) ->
                ipAddress == null ? null : criteriaBuilder.equal(root.get("ipAddress"), "%" + ipAddress + "%"));
    }

    private static Specification<ChangeLog> typeEq(ChangeLogType type) {
        return ((root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.get("type"), type));
    }
}
