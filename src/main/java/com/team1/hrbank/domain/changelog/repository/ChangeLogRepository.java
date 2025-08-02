package com.team1.hrbank.domain.changelog.repository;

import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {
    @Query(value = """
            SELECT *
            FROM change_logs
            WHERE
                (:employeeNumber IS NULL OR employee_number = :employeeNumber)
                AND (:memo IS NULL OR LOWER(memo) LIKE '%' || LOWER(:memo) || '%')
                AND (:ipAddress IS NULL OR LOWER(ip_address) LIKE '%' || LOWER(:ipAddress) || '%')
                AND (:type IS NULL OR type = :type)
                AND (
                    (:from IS NULL AND :to IS NULL)
                    OR (:from IS NULL AND created_at <= :to)
                    OR (:to IS NULL AND created_at >= :from)
                    OR (created_at BETWEEN :from AND :to)
                )
                AND (:lastId IS NULL OR
                    (:direction = 'ASC' AND id > :lastId)
                    OR (:direction = 'DESC' AND id < :lastId)
                )
            LIMIT :limit
            """, nativeQuery = true)
    List<ChangeLog> findAllByConditionWithoutSort(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("lastId") Long lastId,
            @Param("direction") String direction,
            @Param("limit") int limit
    );

    Optional<ChangeLog> findFirstByUpdatedAtDesc();
}
