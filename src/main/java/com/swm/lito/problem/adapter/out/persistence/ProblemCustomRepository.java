package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;

import java.util.List;

public interface ProblemCustomRepository {

    List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, Long subjectId,
                                                         String query, Integer size);
}
