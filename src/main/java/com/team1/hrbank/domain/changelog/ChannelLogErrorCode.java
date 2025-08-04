package com.team1.hrbank.domain.changelog;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChannelLogErrorCode {

    private final int status;
    private final String code;
    private final String message;
}
