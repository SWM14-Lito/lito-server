package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemCustomRepository {

    Page<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                         String query, Pageable pageable);

    List<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Long lastProblemUserId, Integer size);

    List<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long lastFavoriteId, Long subjectId,
                                                                          ProblemStatus problemStatus, Integer size);
}
