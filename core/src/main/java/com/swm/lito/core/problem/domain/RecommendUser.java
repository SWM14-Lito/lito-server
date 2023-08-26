package com.swm.lito.core.problem.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "RECOMMEND_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE RECOMMEND_USER SET status = INACTIVE WHERE id = ?")
@Builder
@ToString
public class RecommendUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "problem_id")
    private Long problemId;

    public static RecommendUser createRecommendUser(Long userId, Long problemId){
        return RecommendUser.builder()
                .userId(userId)
                .problemId(problemId)
                .build();
    }
}
