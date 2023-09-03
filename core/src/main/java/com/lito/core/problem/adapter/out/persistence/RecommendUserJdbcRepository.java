package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.RecommendUser;

import java.util.List;

public interface RecommendUserJdbcRepository {

    void saveRecommendUsers(List<RecommendUser> recommendUsers);
}
