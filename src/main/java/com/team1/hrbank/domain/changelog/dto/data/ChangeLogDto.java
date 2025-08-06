package com.team1.hrbank.domain.changelog.dto.data;

import com.team1.hrbank.domain.changelog.entity.ChangeLogType;

import java.time.Instant;
import java.time.LocalDateTime;

public record ChangeLogDto(
        //직원 정보 수정 이력 목록 조회를 위한 DTO. 상세 변경 내용인 diff를 포함하지 않는다.
        Long id,  //  기본키
        ChangeLogType type, //  이력 유형. enum 타입 (CREATED, UPDATED, DELETED)
        String employeeNumber,  //  대상 직원 사번
        String memo,  //  요청에 관한 간단한 메모 내용
        String ipAddress,  //  해당 요청의 IP 주소
        LocalDateTime at  //  요청 발생 시간
) {
}
