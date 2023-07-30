package com.swm.lito.core.problem.adapter.out.persistence;

import com.swm.lito.core.problem.domain.RecommendUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendUserRepository extends JpaRepository<RecommendUser, Long> {
}
