package com.swm.lito.problem.application.service;

import com.swm.lito.common.exception.ApplicationException;
import com.swm.lito.common.exception.problem.ProblemErrorCode;
import com.swm.lito.common.security.AuthUser;
import com.swm.lito.problem.application.port.in.ProblemQueryUseCase;
import com.swm.lito.problem.application.port.in.response.ProblemPageWithFavoriteResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemPageWithProcessResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemResponseDto;
import com.swm.lito.problem.application.port.in.response.ProblemUserResponseDto;
import com.swm.lito.problem.application.port.out.FavoriteQueryPort;
import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.application.port.out.ProblemUserQueryPort;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.problem.application.service.comparator.ProblemStatusWithFavoriteComparator;
import com.swm.lito.problem.domain.Problem;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        boolean favorite = favoriteQueryPort.existsByUserAndProblem(user, problem);
        return ProblemResponseDto.of(problem, favorite);
    }

    @Override
    public Page<ProblemPageQueryDslResponseDto> findProblemPage(AuthUser authUser, Long subjectId, ProblemStatus problemStatus,
                                                                String query, Pageable pageable) {
        return problemQueryPort.findProblemPage
                (authUser.getUserId(), subjectId, problemStatus,  query, pageable);

    }

    @Override
    public List<ProblemPageWithProcessResponseDto> findProblemPageWithProcess(AuthUser authUser, Long lastProblemUserId, Integer size){
        return ProblemPageWithProcessResponseDto.from(problemQueryPort.findProblemPageWithProcess
                        (authUser.getUserId(), lastProblemUserId, size));
    }

    @Override
    public List<ProblemPageWithFavoriteResponseDto> findProblemPageWithFavorite(AuthUser authUser, Long lastFavoriteId, Long subjectId,
                                                                                ProblemStatus problemStatus, Integer size){
        List<ProblemPageWithFavoriteQResponseDto> qResponseDtos = problemQueryPort.findProblemPageWithFavorite
                (authUser.getUserId(), lastFavoriteId, subjectId, problemStatus, size);

        return ProblemPageWithFavoriteResponseDto.from(qResponseDtos
                .stream()
                .sorted(new ProblemStatusWithFavoriteComparator())
                .collect(Collectors.toList()));
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
