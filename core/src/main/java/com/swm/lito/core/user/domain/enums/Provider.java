package com.swm.lito.core.user.domain.enums;

import com.swm.lito.core.common.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.swm.lito.core.common.exception.auth.AuthErrorCode.INVALID_OAUTH;


@Getter
@AllArgsConstructor
public enum Provider {

    KAKAO("kakao"),
    APPLE("apple");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Provider::getName, Provider::name)));

    public static Provider toEnum(String provider){
        if(Arrays.stream(values()).noneMatch(p->p.name.equals(provider)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(provider));
    }
}
