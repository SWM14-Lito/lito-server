package com.lito.core.problem.domain;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "FAVORITE", uniqueConstraints = {
        @UniqueConstraint(
                name = "user_problem_unique",
                columnNames = {"user_id", "problem_id"}
        )
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE FAVORITE SET status = INACTIVE WHERE id = ?")
@Builder
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public static Favorite createFavorite(User user, Problem problem){
        return Favorite.builder()
                .user(user)
                .problem(problem)
                .build();
    }

}
