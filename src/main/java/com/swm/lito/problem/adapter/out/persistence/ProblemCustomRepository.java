package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;

import java.util.List;

public interface ProblemCustomRepository {

    List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, Long subjectId,
                                                         String query, Integer size);

    List<ProblemPageWithProcessQResponseDto> findProblemWithProcess(Long userId, Long lastProblemUserId, Integer size);
}
