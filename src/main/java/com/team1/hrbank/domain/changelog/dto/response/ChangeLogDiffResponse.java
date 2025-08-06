package com.team1.hrbank.domain.changelog.dto.response;

public record ChangeLogDiffResponse(
        //직원 정보 수정 이력의 상세 정보를 조회를 위한 DTO. 변경 상세 내용(diffs)이 포합된다.
        String propertyName, // 변경 항목(컬럼) 이름
        String before,  //  변경 전 내용
        String after  //  변경 후 내용
) {}