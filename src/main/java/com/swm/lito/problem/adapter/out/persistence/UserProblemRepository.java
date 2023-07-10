package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.domain.ProblemUser;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProblemRepository extends JpaRepository<ProblemUser, Long> {

    Optional<ProblemUser> findFirstByUserAndProblemStatusOrderByCreatedAtDesc(User user, ProblemStatus problemStatus);
}
