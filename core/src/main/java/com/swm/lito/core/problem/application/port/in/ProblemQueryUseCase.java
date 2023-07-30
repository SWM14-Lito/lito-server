package com.swm.lito.core.problem.application.port.in;

import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.problem.application.port.in.response.ProblemResponseDto;
import com.swm.lito.core.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemQueryUseCase {

    ProblemResponseDto find(AuthUser authUser, Long id);
    Page<ProblemPageQueryDslResponseDto> findProblemPage(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                         String query, Pageable pageable);

    ProblemUserResponseDto findProblemUser(AuthUser authUser);

    Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(AuthUser authUser, Pageable pageable);

    Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                                          Pageable pageable);
}
