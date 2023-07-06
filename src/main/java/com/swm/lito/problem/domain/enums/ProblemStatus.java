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
public enum ProblemStatus {

    SUCCESS("풀이완료"),
    FAIL("풀이실패"),
    PROCESS("풀이중");

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ProblemStatus::getName, ProblemStatus::name)));

    public static ProblemStatus toEnum(String problemStatus){
        if(Arrays.stream(values()).noneMatch(p->p.name.equals(problemStatus)))
            throw new ApplicationException(INVALID_OAUTH);
        return valueOf(TYPES.get(problemStatus));
    }
}
