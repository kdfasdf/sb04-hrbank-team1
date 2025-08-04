package com.team1.hrbank.domain.changelog.repository;

import com.team1.hrbank.domain.changelog.entity.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {

    @Query(value = """
            SELECT * FROM change_logs
            WHERE (:employeeNumber IS NULL OR employee_number = :employeeNumber)
              AND (:memo IS NULL OR LOWER(memo) LIKE CONCAT('%', LOWER(:memo), '%'))
              AND (:ipAddress IS NULL OR LOWER(ip_address) LIKE CONCAT('%', LOWER(:ipAddress), '%'))
              AND (:type IS NULL OR type = :type)
              AND (
                  (:from IS NULL AND :to IS NULL)
                  OR (:from IS NULL AND created_at <= :to)
                  OR (:to IS NULL AND created_at >= :from)
                  OR (created_at BETWEEN :from AND :to)
              )
              AND (:lastId IS NULL OR id > :lastId)
            ORDER BY created_at ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<ChangeLog> findAllOrderByCreatedAtAsc(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("lastId") Long lastId,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT * FROM change_logs
            WHERE (:employeeNumber IS NULL OR employee_number = :employeeNumber)
              AND (:memo IS NULL OR LOWER(memo) LIKE CONCAT('%', LOWER(:memo), '%'))
              AND (:ipAddress IS NULL OR LOWER(ip_address) LIKE CONCAT('%', LOWER(:ipAddress), '%'))
              AND (:type IS NULL OR type = :type)
              AND (
                  (:from IS NULL AND :to IS NULL)
                  OR (:from IS NULL AND created_at <= :to)
                  OR (:to IS NULL AND created_at >= :from)
                  OR (created_at BETWEEN :from AND :to)
              )
              AND (:lastId IS NULL OR id < :lastId)
            ORDER BY created_at DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<ChangeLog> findAllOrderByCreatedAtDesc(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("lastId") Long lastId,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT * FROM change_logs
            WHERE (:employeeNumber IS NULL OR employee_number = :employeeNumber)
              AND (:memo IS NULL OR LOWER(memo) LIKE CONCAT('%', LOWER(:memo), '%'))
              AND (:ipAddress IS NULL OR LOWER(ip_address) LIKE CONCAT('%', LOWER(:ipAddress), '%'))
              AND (:type IS NULL OR type = :type)
              AND (
                  (:from IS NULL AND :to IS NULL)
                  OR (:from IS NULL AND created_at <= :to)
                  OR (:to IS NULL AND created_at >= :from)
                  OR (created_at BETWEEN :from AND :to)
              )
              AND (:lastId IS NULL OR id > :lastId)
            ORDER BY ip_address ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<ChangeLog> findAllOrderByIpAddressAsc(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("lastId") Long lastId,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT * FROM change_logs
            WHERE (:employeeNumber IS NULL OR employee_number = :employeeNumber)
              AND (:memo IS NULL OR LOWER(memo) LIKE CONCAT('%', LOWER(:memo), '%'))
              AND (:ipAddress IS NULL OR LOWER(ip_address) LIKE CONCAT('%', LOWER(:ipAddress), '%'))
              AND (:type IS NULL OR type = :type)
              AND (
                  (:from IS NULL AND :to IS NULL)
                  OR (:from IS NULL AND created_at <= :to)
                  OR (:to IS NULL AND created_at >= :from)
                  OR (created_at BETWEEN :from AND :to)
              )
              AND (:lastId IS NULL OR id < :lastId)
            ORDER BY ip_address DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<ChangeLog> findAllOrderByIpAddressDesc(
            @Param("employeeNumber") String employeeNumber,
            @Param("memo") String memo,
            @Param("ipAddress") String ipAddress,
            @Param("type") String type,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("lastId") Long lastId,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT COUNT(*) 
            FROM change_logs
            WHERE
                (:employeeNumber IS NULL OR employee_number = :employeeNumber)
                AND (:memo IS NULL OR LOWER(memo) LIKE CONCAT('%', LOWER(:memo), '%'))
                AND (:ipAddress IS NULL OR LOWER(ip_address) LIKE CONCAT('%', LOWER(:ipAddress), '%'))
                AND (:type IS NULL OR CAST(type AS VARCHAR) = :type)
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

    @Query(value = """
            SELECT COUNT(*)
            FROM change_logs
            WHERE created_at BETWEEN :from AND :to
            """, nativeQuery = true)
    long countByCreatedAtBetween(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    Optional<ChangeLog> findFirstByOrderByUpdatedAtDesc();
}
