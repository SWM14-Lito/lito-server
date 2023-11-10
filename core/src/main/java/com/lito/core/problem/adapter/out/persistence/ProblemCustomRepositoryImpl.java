package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.lito.core.problem.application.port.out.response.ProblemPageWithProcessQResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.lito.core.problem.application.port.out.response.*;
import com.lito.core.problem.domain.Favorite;
import com.lito.core.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lito.core.problem.domain.QFavorite.favorite;
import static com.lito.core.problem.domain.QProblem.problem;
import static com.lito.core.problem.domain.QProblemUser.problemUser;
import static com.lito.core.problem.domain.QSubject.subject;

@RequiredArgsConstructor
public class ProblemCustomRepositoryImpl implements ProblemCustomRepository {

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
                .leftJoin(problemUser).on(problem.id.eq(problemUser.problem.id), problemUser.user.id.eq(userId))
                .where(eqSubjectId(subjectId), eqProblemStatus(problemStatus),
                        containQuery(query))
                .orderBy(statusOrder.desc(), problem.id.desc())
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
                .leftJoin(problemUser).on(problem.id.eq(problemUser.problem.id), problemUser.user.id.eq(userId))
                .where(eqSubjectId(subjectId), eqProblemStatus(problemStatus),
                        containQuery(query));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ProblemPageWithProcessQResponseDto> findProblemPageWithProcess(Long userId, Pageable pageable) {
        List<ProblemPageWithProcessQResponseDto> content = queryFactory.select(
                    new QProblemPageWithProcessQResponseDto(
                            problemUser.id, problem.id, subject.subjectName, problem.question
                    )
                )
                .from(problemUser)
                .innerJoin(problemUser.problem)
                .innerJoin(problem.subject)
                .where(problemUser.user.id.eq(userId), problemUser.problemStatus.eq(ProblemStatus.PROCESS))
                .orderBy(problemUser.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> problemIds = content
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
        content.forEach( r -> r.setFavorite(problemIdVsFavoriteId.get(r.getProblemId()) != null));

        JPAQuery<Long> countQuery = queryFactory
                .select(problemUser.count())
                .where(problemUser.user.id.eq(userId), problemUser.problemStatus.eq(ProblemStatus.PROCESS));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ProblemPageWithFavoriteQResponseDto> findProblemPageWithFavorite(Long userId, Long subjectId, ProblemStatus problemStatus,
                                                                                 Pageable pageable) {
        NumberExpression<Integer> statusOrder = new CaseBuilder()
                .when(problemUser.problemStatus.eq(ProblemStatus.PROCESS)).then(3)
                .when(problemUser.problemStatus.eq(ProblemStatus.COMPLETE)).then(1)
                .otherwise(2);

        List<ProblemPageWithFavoriteQResponseDto> content = queryFactory.select(
                    new QProblemPageWithFavoriteQResponseDto(
                            favorite.id, problem.id, subject.subjectName, problem.question, problemUser.problemStatus
                    )
                )
                .from(favorite)
                .innerJoin(favorite.problem, problem)
                .innerJoin(problem.subject, subject)
                .leftJoin(problemUser).on(favorite.problem.id.eq(problemUser.problem.id), favorite.user.id.eq(problemUser.user.id))
                .where(eqSubjectId(subjectId), eqProblemStatus(problemStatus),
                        favorite.user.id.eq(userId))
                .orderBy(statusOrder.desc(), favorite.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(favorite.count())
                .where(eqSubjectId(subjectId), eqProblemStatus(problemStatus),
                        favorite.user.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ProblemReviewPageQResponseDto> findProblemReviewPage(Long userId, Pageable pageable){
        List<ProblemReviewPageQResponseDto> content = queryFactory.select(
                        new QProblemReviewPageQResponseDto(
                                problem.id, subject.subjectName, problem.question, problemUser.problemStatus, problemUser.unsolvedCnt
                        )
                )
                .from(problemUser)
                .innerJoin(problemUser.problem)
                .innerJoin(problem.subject)
                .where(problemUser.user.id.eq(userId), problemUser.unsolvedCnt.goe(1))
                .orderBy(problemUser.unsolvedCnt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> problemIds = content.stream()
                .map(ProblemReviewPageQResponseDto::getProblemId)
                .toList();

        List<Favorite> favorites = getFavorites(userId,problemIds);

        Map<Long, Long> problemIdVsFavoriteId = favorites
                .stream()
                .collect(Collectors.toMap(
                        f1 -> f1.getProblem().getId(),
                        f1 -> f1.getId()
                ));
        content.forEach( r -> r.setFavorite(problemIdVsFavoriteId.get(r.getProblemId()) != null));

        JPAQuery<Long> countQuery = queryFactory
                .select(problemUser.count())
                .where(problemUser.user.id.eq(userId), problemUser.unsolvedCnt.goe(1));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqSubjectId(Long subjectId){
        if(subjectId == null)   return null;
        return problem.subject.id.eq(subjectId);
    }

    private BooleanExpression eqProblemStatus(ProblemStatus problemStatus){
        if(problemStatus == null)   return null;
        else if(problemStatus == ProblemStatus.COMPLETE){
            return problemUser.problemStatus.eq(ProblemStatus.COMPLETE);
        }
        return problemUser.problemStatus.eq(ProblemStatus.PROCESS).or(problemUser.isNull());

    }

    private BooleanExpression containQuery(String query){
        if(!StringUtils.hasText(query))  return null;
        return problem.question.contains(query);
    }

    private List<Favorite> getFavorites(Long userId, List<Long> problemIds){
        return queryFactory.selectFrom(favorite)
                .where(favorite.user.id.eq(userId), favorite.status.eq(BaseEntity.Status.ACTIVE),
                        favorite.problem.id.in(problemIds))
                .fetch();
    }
}
