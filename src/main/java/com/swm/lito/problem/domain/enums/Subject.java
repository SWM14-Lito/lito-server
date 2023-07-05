package com.swm.lito.problem.domain.enums;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.user.domain.enums.Provider;
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
public enum Subject {

    OS("운영체제"),
    NETWORK("네트워크"),
    DB("데이터 베이스"),
    DS("자료구조 및 알고리즘");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Subject::getName, Subject::name)));

    public static Subject toEnum(String subject){
        if(Arrays.stream(values()).noneMatch(p->p.name.equals(subject)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(subject));
    }
}
