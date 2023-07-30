package com.swm.lito.core.problem.adapter.out.persistence;

import com.swm.lito.core.problem.domain.Problem;
import com.swm.lito.core.problem.domain.ProblemUser;
import com.swm.lito.core.problem.domain.enums.ProblemStatus;
import com.swm.lito.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemUserRepository extends JpaRepository<ProblemUser, Long> {

    Optional<ProblemUser> findFirstByUserAndProblemStatusOrderByCreatedAtDesc(User user, ProblemStatus problemStatus);
    Optional<ProblemUser> findByProblemAndUser(Problem problem, User user);
}
