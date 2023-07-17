package com.swm.lito.problem.domain;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.problem.domain.enums.ProblemStatus;
import com.swm.lito.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "PROBLEM_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE PROBLEM_USER SET status = INACTIVE WHERE id = ?")
@Builder
public class ProblemUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "problem_status")
    private ProblemStatus problemStatus;

    public static ProblemUser createProblemUser(Problem problem, User user){
        return ProblemUser.builder()
                .problem(problem)
                .user(user)
                .problemStatus(ProblemStatus.PROCESS)
                .build();
    }


}
