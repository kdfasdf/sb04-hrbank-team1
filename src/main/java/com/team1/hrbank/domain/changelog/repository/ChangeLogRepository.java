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
            ORDER BY
                CASE WHEN :sortKey = 'CREATED_AT_ASC' THEN created_at END ASC,
                CASE WHEN :sortKey = 'CREATED_AT_DESC' THEN created_at END DESC,
                CASE WHEN :sortKey = 'IP_ADDRESS_ASC' THEN ip_address END ASC,
                CASE WHEN :sortKey = 'IP_ADDRESS_DESC' THEN ip_address END DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<ChangeLog> findAllByCondition(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("lastId") Long lastId,
            @Param("direction") String direction,
            @Param("sortKey") String sortKey,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT COUNT(*) 
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
            """, nativeQuery = true)
    long countByCondition(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("SELECT c FROM ChangeLog c ORDER BY c.updatedAt DESC")
    Optional<ChangeLog> findFirstByUpdatedAtDesc();
}
