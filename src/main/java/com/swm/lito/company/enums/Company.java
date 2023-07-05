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
public enum Company {

    NAVER("네이버"),
    KAKAO("카카오"),
    LINE("라인"),
    COUPANG("쿠팡"),
    BAEMIN("배민"),
    CARROT("당근"),
    TOSS("토스"),
    LARGE("대기업"),
    FINANCE("금융"),
    STARTUP("스타트업");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Company::getName, Company::name)));

    public static Company toEnum(String company){
        if(Arrays.stream(values()).noneMatch(c->c.name.equals(company)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(company));
    }

}
