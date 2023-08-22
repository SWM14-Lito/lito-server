package com.swm.lito.core.problem.adapter.out.persistence;

import com.swm.lito.core.problem.domain.RecommendUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RecommendUserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendUserJdbcRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveRecommendUsers(List<RecommendUser> recommendUsers){
        String sql = "INSERT INTO RECOMMEND_USER (user_id, problem_id, status, created_at, updated_at) " +
                " VALUES (?, ?, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        jdbcTemplate.batchUpdate(sql,
                recommendUsers,
                recommendUsers.size(),
                (PreparedStatement ps, RecommendUser recommendUser) ->{
                    ps.setLong(1, recommendUser.getUserId());
                    ps.setLong(2, recommendUser.getProblemId());
                });
    }
}
