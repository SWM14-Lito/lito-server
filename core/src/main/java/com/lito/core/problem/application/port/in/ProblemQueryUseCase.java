package com.lito.core.problem.application.port.in;

import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.application.port.in.response.ProblemHomeResponseDto;
import com.lito.core.problem.application.port.in.response.ProblemResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.enums.ProblemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemQueryUseCase {

    ProblemResponseDto find(AuthUser authUser, Long id);
    Page<ProblemPageQueryDslResponseDto> findProblemPage(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                         String query, Pageable pageable);

    ProblemHomeResponseDto findHome(AuthUser authUser);

    Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(AuthUser authUser, Pageable pageable);

    Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                                          Pageable pageable);
    List<ProblemUser> findAllProblemUser();

    List<Problem> findAllProblem();
}
