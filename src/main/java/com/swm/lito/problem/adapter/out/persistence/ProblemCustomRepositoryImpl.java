package com.swm.lito.problem.adapter.out.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.application.port.out.response.QProblemPageQueryDslResponseDto;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.swm.lito.problem.domain.QFavorite.favorite;
import static com.swm.lito.problem.domain.QProblem.problem;
import static com.swm.lito.problem.domain.QProblemUser.problemUser;
import static com.swm.lito.problem.domain.QSubject.subject;

@RequiredArgsConstructor
public class ProblemCustomRepositoryImpl implements ProblemCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, String subjectName,
                                                                String query, Integer size) {

        List<ProblemPageQueryDslResponseDto> result = queryFactory.select(
                        new QProblemPageQueryDslResponseDto(
                                problem.id, subject.subjectName, problem.question
                        ))
                .from(problem)
                .innerJoin(problem.subject, subject)
                .where(eqSubject(subjectName), containQuery(query),
                        ltProblemId(lastProblemId))
                .orderBy(problem.id.desc())
                .limit(size)
                .fetch();
        result.forEach(r -> {
            r.setProblemStatus(getProblemStatus(userId, r.getProblemId()));
            r.setFavorite(existsFavorite(userId, r.getProblemId()));
        });
        return result;
    }

    private BooleanExpression eqSubject(String subject){
        if(!StringUtils.hasText(subject))   return null;
        return problem.subject.subjectName.eq(subject);
    }

    private BooleanExpression ltProblemId(Long lastProblemId){
        if(lastProblemId == null) return null;
        return problem.id.lt(lastProblemId);
    }

    private BooleanExpression containQuery(String query){
        if(!StringUtils.hasText(query))  return null;
        return problem.question.contains(query);
    }

    private ProblemStatus getProblemStatus(Long userId, Long problemId){
        return queryFactory.select(problemUser.problemStatus)
                .from(problemUser)
                .where(problemUser.user.id.eq(userId), problemUser.problem.id.eq(problemId))
                .fetchOne();
    }

    private boolean existsFavorite(Long userId, Long problemId){
        return queryFactory.selectOne()
                .from(favorite)
                .where(favorite.user.id.eq(userId), favorite.problem.id.eq(problemId))
                .fetchFirst() != null;
    }
}
