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
public enum CareerStatus {

    NEWBIE("~1년차"),
    JUNIOR("1~5년차"),
    SENIOR("5년차 이상");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(CareerStatus::getName, CareerStatus::name)));

    public static CareerStatus toEnum(String careerStatus){
        if(Arrays.stream(values()).noneMatch(c->c.name.equals(careerStatus)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(careerStatus));
    }
}
