package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.application.port.out.ProblemQueryPort;
import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;
import com.swm.lito.problem.domain.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemQueryAdapter implements ProblemQueryPort {

    private final ProblemRepository problemRepository;


    @Override
    public Optional<Problem> findProblemById(Long id) {
        return problemRepository.findProblemById(id);
    }

    @Override
    public List<ProblemPageQueryDslResponseDto> findProblemPage(Long userId, Long lastProblemId, String subjectName,
                                                                String query, Integer size) {
        return problemRepository.findProblemPage(userId, lastProblemId, subjectName, query, size);
    }
}
