package com.swm.lito.core.problem.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@Getter
@AllArgsConstructor
public enum ProblemStatus {

    //DB에 저장되는 값
    COMPLETE("풀이완료"),
    PROCESS("풀이중"),
    //풀지않은 상태값을 표현하기 위한 비즈니스 로직 용도
    NOT_SEEN("풀지않음"),
    ;

    private String name;
    private static final Map<String,String> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(ProblemStatus::getName, ProblemStatus::name)));
}
