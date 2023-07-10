package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.domain.Problem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @EntityGraph(attributePaths = {"subject","subjectCategory"})
    Optional<Problem> findById(Long id);

}
