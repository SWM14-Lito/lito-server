package com.swm.lito.core.problem.application.port.out;

import com.swm.lito.core.problem.domain.RecommendUser;

import java.util.List;

public interface RecommendUserQueryPort {

    List<RecommendUser> findRecommendUsers(Long userId);
}
