package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.Problem;
import com.lito.core.problem.domain.ProblemUser;
import com.lito.core.problem.domain.enums.ProblemStatus;
import com.lito.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProblemUserRepository extends JpaRepository<ProblemUser, Long> {

    Optional<ProblemUser> findFirstByUserAndProblemStatusOrderByCreatedAtDesc(User user, ProblemStatus problemStatus);
    Optional<ProblemUser> findByProblemAndUser(Problem problem, User user);
    int countProblemUserByUserAndProblemStatusAndUpdatedAtIsBetween(User user, ProblemStatus problemStatus, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
