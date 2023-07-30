package com.swm.lito.core.problem.adapter.out.persistence;

import com.swm.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemCustomRepository {

    Page<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                         String query, Pageable pageable);

    Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Pageable pageable);

    Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                                          Pageable pageable);
}
