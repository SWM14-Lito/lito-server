package com.lito.core.admin.adapter.out;

import com.lito.core.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminProblemRepository extends JpaRepository<Problem, Long> {
}
