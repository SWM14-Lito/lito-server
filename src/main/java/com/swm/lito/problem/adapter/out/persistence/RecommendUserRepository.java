package com.swm.lito.problem.adapter.out.persistence;

import com.swm.lito.problem.domain.RecommendUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendUserRepository extends JpaRepository<RecommendUser, Long> {
}
