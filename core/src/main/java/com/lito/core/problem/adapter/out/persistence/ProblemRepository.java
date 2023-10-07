package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.Problem;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemCustomRepository {

    @Query(value = "select distinct p from Problem p" +
            " join fetch p.subject" +
            " left join fetch p.faqs" +
            " where p.id = :id")
    Optional<Problem> findProblemWithFaqById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"subject"})
    Optional<Problem> findProblemById(Long id);

    List<Problem> findTop3ByOrderByCreatedAtAsc();

}
