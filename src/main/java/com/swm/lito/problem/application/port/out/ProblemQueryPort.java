package com.swm.lito.problem.application.port.out;


import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ProblemQueryPort {

    Optional<Problem> findProblemWithFaqById(Long id);

    List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, Long subjectId,
                                                         String query, Integer size);
    Optional<Problem> findProblemById(Long id);

    List<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Long lastProblemUserId, Integer size);

    List<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long lastFavoriteId, Long subjectId,
                                                                          Integer size);
}
