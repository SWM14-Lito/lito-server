package com.lito.core.problem.application.port.out;


import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.enums.ProblemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProblemQueryPort {

    Optional<Problem> findProblemWithFaqById(Long id);

    Page<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                         String query, Pageable pageable);
    Optional<Problem> findProblemById(Long id);

    Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Pageable pageable);

    Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                                          Pageable pageable);

    List<Problem> findAllProblem();

    List<Problem> findRandomProblems();
}
