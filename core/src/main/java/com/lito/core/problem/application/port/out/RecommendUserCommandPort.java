package com.lito.core.problem.application.port.out;

import com.lito.core.problem.domain.RecommendUser;

import java.util.List;

public interface RecommendUserCommandPort {

    void saveRecommendUsers(List<RecommendUser> recommendUsers);
}
