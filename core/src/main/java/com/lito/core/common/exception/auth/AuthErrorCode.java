package com.lito.core.common.exception.auth;

import com.lito.core.common.exception.ErrorEnumCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorEnumCode {
    EXPIRED("A001", "토큰이 만료되었습니다."),
    INVALID_SIGNATURE("A002", "유효하지 않은 서명입니다."),
    INVALID_JWT("A003", "유효하지 않은 토큰입니다."),
    UNSUPPORTED("A004", "지원하지 않는 토큰입니다."),
    EMPTY_CLAIM("A005", "Claim이 존재하지 않습니다."),
    EMPTY_JWT("A006","토큰이 입력되지 않았습니다."),
    FORBIDDEN("A007", "권한이 없는 유저입니다."),
    UNAUTHORIZED("A008","인증되지 않은 유저입니다."),
    NOT_FOUND_REFRESH_TOKEN("A009","존재하지 않는 Refresh Token 입니다."),
    INVALID_REFRESH_TOKEN("A010", "유효하지 않은 Refresh Token 입니다."),
    INVALID_OAUTH("A011","지원되지 않는 Oauth 제공자입니다.")
    ;

    private final String code;
    private final String message;

}
