package com.swm.lito.problem.adapter.out.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swm.lito.problem.application.port.out.response.*;
import com.swm.lito.problem.domain.Favorite;
import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.swm.lito.problem.domain.QFavorite.favorite;
import static com.swm.lito.problem.domain.QProblem.problem;
import static com.swm.lito.problem.domain.QProblemUser.problemUser;
import static com.swm.lito.problem.domain.QSubject.subject;

@RequiredArgsConstructor
public class ProblemCustomRepositoryImpl implements ProblemCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                                String query, Pageable pageable) {
        NumberExpression<Integer> statusOrder = new CaseBuilder()
                .when(problemUser.problemStatus.eq(ProblemStatus.PROCESS)).then(3)
                .when(problemUser.problemStatus.eq(ProblemStatus.COMPLETE)).then(1)
                .otherwise(2);

        List<ProblemPageQueryDslResponseDto> content = queryFactory.select(
                        new QProblemPageQueryDslResponseDto(
                                problem.id, subject.subjectName, problem.question, problemUser.problemStatus
                        ))
                .from(problem)
                .innerJoin(problem.subject)
                .leftJoin(problemUser).on(problem.id.eq(problemUser.problem.id))
                .where(eqSubjectId(subjectId), containQuery(query))
                .orderBy(statusOrder.desc(), problemUser.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> problemIds = content
                .stream()
                .map(ProblemPageQueryDslResponseDto::getProblemId)
                .toList();
        List<Favorite> favorites = getFavorites(userId,problemIds);

        Map<Long, Long> problemIdVsFavoriteId = favorites
                .stream()
                        .collect(Collectors.toMap(
                                f1 -> f1.getProblem().getId(),
                                f1 -> f1.getId()
                        ));
        content.forEach(c -> c.setFavorite(problemIdVsFavoriteId.get(c.getProblemId()) != null));

        JPAQuery<Long> countQuery = queryFactory
                .select(problem.count())
                .from(problem)
                .innerJoin(problem.subject)
                .leftJoin(problemUser).on(problem.id.eq(problemUser.problem.id))
                .where(eqSubjectId(subjectId), eqProblemStatus(problemStatus, userId),
                        containQuery(query));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Long lastProblemUserId, Integer size) {
        List<ProblemPageWithProcessQResponseDto> result = queryFactory.select(
                    new QProblemPageWithProcessQResponseDto(
                            problemUser.id, problem.id, subject.subjectName, problem.question
                    )
                )
                .from(problemUser)
                .innerJoin(problemUser.problem)
                .innerJoin(problem.subject)
                .where(ltProblemUserId(lastProblemUserId), problemUser.user.id.eq(userId),
                        problemUser.problemStatus.eq(ProblemStatus.PROCESS))
                .orderBy(problemUser.id.desc())
                .limit(size)
                .fetch();

        List<Long> problemIds = result
                .stream()
                .map(ProblemPageWithProcessQResponseDto::getProblemId)
                .toList();

        List<Favorite> favorites = getFavorites(userId,problemIds);

        Map<Long, Long> problemIdVsFavoriteId = favorites
                .stream()
                .collect(Collectors.toMap(
                        f1 -> f1.getProblem().getId(),
                        f1 -> f1.getId()
                ));
        result.forEach( r -> r.setFavorite(problemIdVsFavoriteId.get(r.getProblemId()) != null));

        return result;
    }

    @Override
    public List<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long lastFavoriteId, Long subjectId,
                                                                                 ProblemStatus problemStatus, Integer size) {
        List<ProblemPageWithFavoriteQResponseDto> result = queryFactory.select(
                    new QProblemPageWithFavoriteQResponseDto(
                            favorite.id, problem.id, subject.subjectName, problem.question
                    )
                )
                .from(favorite)
                .innerJoin(favorite.problem)
                .innerJoin(problem.subject)
                .where(ltFavoriteId(lastFavoriteId), eqSubjectId(subjectId),
                        eqProblemStatus(problemStatus, userId), favorite.user.id.eq(userId))
                .orderBy(favorite.id.desc())
                .limit(size)
                .fetch();

        List<Long> problemIds = result
                .stream()
                .map(ProblemPageWithFavoriteQResponseDto::getProblemId)
                .toList();

        List<ProblemUser> problemUsers = getProblemUsers(userId, problemIds);

        Map<Long, ProblemStatus> problemIdVsStatusMap = problemUsers
                .stream()
                .collect(Collectors.toMap(
                        p1 -> p1.getProblem().getId(),
                        p2 -> p2.getProblemStatus()
                ));

        result.forEach( r -> r.setProblemStatus(problemIdVsStatusMap.get(r.getProblemId())));

        return result;
    }

    private BooleanExpression eqSubjectId(Long subjectId){
        if(subjectId == null)   return null;
        return problem.subject.id.eq(subjectId);
    }

    private BooleanExpression eqProblemStatus(ProblemStatus problemStatus, Long userId){
        if(problemStatus == null)   return null;
        else if(problemStatus == ProblemStatus.COMPLETE){
            return problem.id.in(
                    JPAExpressions
                        .select(problemUser.problem.id)
                        .from(problemUser)
                        .where(problemUser.problemStatus.eq(problemStatus), problemUser.user.id.eq(userId)));
        }
        else return problem.id.notIn(
                JPAExpressions
                    .select(problemUser.problem.id)
                    .from(problemUser)
                    .where(problemUser.problemStatus.eq(ProblemStatus.COMPLETE), problemUser.user.id.eq(userId)));

    }

    private BooleanExpression ltProblemUserId(Long lastProblemUserId){
        if(lastProblemUserId == null)   return null;
        return problemUser.id.lt(lastProblemUserId);
    }

    private BooleanExpression ltFavoriteId(Long lastFavoriteId){
        if(lastFavoriteId == null)  return null;
        return favorite.id.lt(lastFavoriteId);
    }

    private BooleanExpression containQuery(String query){
        if(!StringUtils.hasText(query))  return null;
        return problem.question.contains(query);
    }

    private List<ProblemUser> getProblemUsers(Long userId, List<Long> problemIds){
        return queryFactory.select(problemUser)
                .from(problemUser)
                .where(problemUser.user.id.eq(userId),problemUser.problem.id.in(problemIds))
                .fetch();
    }

    private List<Favorite> getFavorites(Long userId, List<Long> problemIds){
        return queryFactory.selectFrom(favorite)
                .where(favorite.user.id.eq(userId), favorite.problem.id.in(problemIds))
                .fetch();
    }
}
