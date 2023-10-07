package com.lito.core.problem.application.service;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.problem.ProblemErrorCode;
import com.lito.core.common.security.AuthUser;
import com.lito.core.problem.application.port.in.response.ProblemHomeResponseDto;
import com.lito.core.problem.application.port.in.response.ProcessProblemResponseDto;
import com.lito.core.problem.application.port.in.response.RecommendUserResponseDto;
import com.lito.core.problem.application.port.out.RecommendUserQueryPort;
import com.lito.core.problem.domain.Favorite;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.RecommendUser;
import com.lito.core.user.domain.User;
import com.lito.core.problem.application.port.in.ProblemQueryUseCase;
import com.lito.core.problem.application.port.in.response.ProblemResponseDto;
import com.lito.core.problem.application.port.out.FavoriteQueryPort;
import com.lito.core.problem.application.port.out.ProblemQueryPort;
import com.lito.core.problem.application.port.out.ProblemUserQueryPort;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemQueryService implements ProblemQueryUseCase{

    private final ProblemQueryPort problemQueryPort;
    private final ProblemUserQueryPort problemUserQueryPort;
    private final FavoriteQueryPort favoriteQueryPort;
    private final RecommendUserQueryPort recommendUserQueryPort;

    @Override
    public ProblemResponseDto find(AuthUser authUser, Long id){
        User user = authUser.getUser();
        Problem problem = problemQueryPort.findProblemWithFaqById(id)
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        ProblemUser problemUser = problemUserQueryPort.findByProblemAndUser(problem, user)
                .orElse(null);
        Optional<Favorite> favorite = favoriteQueryPort.findByUserAndProblem(user, problem);
        boolean flag = favorite.map(f -> f.getStatus() == BaseEntity.Status.ACTIVE).orElse(false);

        return ProblemResponseDto.of(problem, problemUser, flag);
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
    public ProblemHomeResponseDto findHome(AuthUser authUser) {
        User user = authUser.getUser();
        ProblemUser problemUser = problemUserQueryPort.findFirstProblemUser(user)
                .orElse(null);
        int completeProblemCntInToday = getCompleteProblemCntInToday(user);
        Problem problem = problemUser != null ? problemQueryPort.findProblemById(problemUser.getProblem().getId())
                .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND)) : null;
        Optional<Favorite> favorite = favoriteQueryPort.findByUserAndProblem(user, problem);
        boolean flag = favorite.map(f -> f.getStatus() == BaseEntity.Status.ACTIVE).orElse(false);

        List<RecommendUserResponseDto> recommendUserResponseDtos = getRecommendUserResponseDtos(user);
        if(recommendUserResponseDtos.isEmpty()){
            List<Problem> randomProblems = problemQueryPort.findRandomProblems();

            randomProblems.forEach(p -> {
                ProblemUser problemUserByRandomProblem = problemUserQueryPort.findByProblemAndUser(p, user)
                        .orElse(null);

                ProblemStatus problemStatus = (problemUserByRandomProblem != null)
                        ? problemUserByRandomProblem.getProblemStatus()
                        : ProblemStatus.NOT_SEEN;

                boolean flagByRandomProblem = favoriteQueryPort.findByUserAndProblem(user, p)
                        .map(f -> f.getStatus() == BaseEntity.Status.ACTIVE)
                        .orElse(false);

                recommendUserResponseDtos.add(RecommendUserResponseDto.of(p, problemStatus, flagByRandomProblem));
            });
        }

        return problem != null
                ? ProblemHomeResponseDto.of(user,completeProblemCntInToday, ProcessProblemResponseDto.of(problem, flag), recommendUserResponseDtos)
                : ProblemHomeResponseDto.of(user,completeProblemCntInToday, recommendUserResponseDtos);
    }

    private int getCompleteProblemCntInToday(User user) {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return problemUserQueryPort.countCompleteProblemCntInToday(user, ProblemStatus.COMPLETE, startDatetime, endDatetime);
    }

    private List<RecommendUserResponseDto> getRecommendUserResponseDtos(User user) {
        List<RecommendUser> recommendUsers = recommendUserQueryPort.findRecommendUsers(user.getId());
        return recommendUsers
                .stream()
                .map(recommendUser -> {
                    Problem problemByRecommendUser = problemQueryPort.findProblemById(recommendUser.getProblemId())
                            .orElseThrow(() -> new ApplicationException(ProblemErrorCode.PROBLEM_NOT_FOUND));
                    ProblemUser problemUserByRecommendUser = problemUserQueryPort.findByProblemAndUser(problemByRecommendUser, user)
                            .orElse(null);
                    ProblemStatus problemStatus = problemUserByRecommendUser != null
                            ? problemUserByRecommendUser.getProblemStatus()
                            : ProblemStatus.NOT_SEEN;
                    Optional<Favorite> favoriteByRecommendUser = favoriteQueryPort.findByUserAndProblem(user, problemByRecommendUser);
                    boolean flagByRecommendUser = favoriteByRecommendUser.map(f -> f.getStatus() == BaseEntity.Status.ACTIVE).orElse(false);

                    return RecommendUserResponseDto.of(problemByRecommendUser, problemStatus, flagByRecommendUser);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProblemUser> findAllProblemUser(){
        return problemUserQueryPort.findAllProblemUser();
    }

    @Override
    public List<Problem> findAllProblem(){
        return problemQueryPort.findAllProblem();
    }
}
