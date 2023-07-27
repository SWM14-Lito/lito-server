package com.swm.lito.problem.application.port.in;

import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.response.ProblemPageWithFavoriteResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemPageWithProcessResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemQueryUseCase {

    ProblemResponseDto find(AuthUser authUser, Long id);
    Page<ProblemPageQueryDslResponseDto> findProblemPage(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                         String query, Pageable pageable);

    ProblemUserResponseDto findProblemUser(AuthUser authUser);

    Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(AuthUser authUser, Pageable pageable);

    List<ProblemPageWithFavoriteResponseDto> findProblemPageWithFavorite(AuthUser authUser, Long lastFavoriteId, Long subjectId,
                                                                         ProblemStatus problemStatus, Integer size);
}
