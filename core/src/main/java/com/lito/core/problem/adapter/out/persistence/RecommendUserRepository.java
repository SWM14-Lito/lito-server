package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.RecommendUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendUserRepository extends JpaRepository<RecommendUser, Long> {

    List<RecommendUser> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);
}
