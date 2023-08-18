package com.swm.lito.core.problem.application.service;

import com.swm.lito.core.common.exception.ApplicationException;
import com.swm.lito.core.common.exception.problem.ProblemErrorCode;
import com.swm.lito.core.common.security.AuthUser;
import com.swm.lito.core.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.core.problem.application.port.in.response.ProblemResponseDto;
import com.swm.lito.core.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.core.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.core.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.core.problem.application.port.out.ProblemUserQueryPort;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.problem.domain.ProblemUser;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import com.swm.lito.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemQueryService implements ProblemQueryUseCase{

    private final ProblemQueryPort problemQueryPort;
    private final ProblemUserQueryPort problemUserQueryPort;
    private final FavoriteQueryPort favoriteQueryPort;

    @Override
    public ProblemResponseDto find(AuthUser authUser, Long id){
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemWithFaqById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        ProblemUser problemUser = problemUserQueryPort.findByProblemAndUser(problem, user)
                .orElse(null);
        boolean favorite = favoriteQueryPort.existsByUserAndProblem(user, problem);
        return ProblemResponseDto.of(problem, problemUser, favorite);
    }

    @Override
    public Page<ProblemPageQueryDslResponseDto> findProblemPage(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                                String query, Pageable pageable) {
        return problemQueryPort.findProblemPage
                (authUser.getUserId(), subjectId, problemStatus,  query, pageable);

    }

    @Override
    public Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(AuthUser authUser, Pageable pageable){
        return problemQueryPort.findProblemPageWithProcess(authUser.getUserId(), pageable);
    }

    @Override
    public Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                                                Pageable pageable) {
        return problemQueryPort.findProblemPageWithFavorite(authUser.getUserId(), subjectId, problemStatus, pageable);
    }

    @Override
    public ProblemUserResponseDto findProblemUser(AuthUser authUser) {
        User user = authUser.getUser();
        ProblemUser problemUser = problemUserQueryPort.findFirstProblemUser(user)
                .orElse(null);
        Problem problem = problemUser != null ? problemQueryPort.findProblemById(problemUser.getProblem().getId())
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND)) : null;
        boolean favorite = problemUser != null && favoriteQueryPort.existsByUserAndProblem(user, problem);
        return problem != null ? ProblemUserResponseDto.of(user, problem, favorite)
                : ProblemUserResponseDto.of(user);
    }
}
