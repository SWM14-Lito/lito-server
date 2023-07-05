package com.swm.lito.company.enums;

import com.swm.lito.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.swm.lito.common.exception.infrastructure.InfraErrorCode.INVALID_OAUTH;

@Getter
@AllArgsConstructor
public enum Position {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    IOS("iOS"),
    ANDROID("Android"),
    INFRA("인프라"),
    DATA("데이터"),
    SECURITY("보안"),
    AI("인공지능");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Position::getName, Position::name)));

    public static Position toEnum(String position){
        if(Arrays.stream(values()).noneMatch(p->p.name.equals(position)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(position));
    }
}
