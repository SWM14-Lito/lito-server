package com.lito.core.problem.domain;

import com.lito.core.common.entity.BaseEntity;
import com.lito.core.problem.domain.enums.ProblemStatus;
import com.lito.core.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "PROBLEM_USER", uniqueConstraints = {
        @UniqueConstraint(
                name = "problem_user_unique",
                columnNames = {"problem_id","user_id"}
        )
})
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

    @Column(name = "unsolved_cnt")
    private int unsolvedCnt;

    public static ProblemUser createProblemUser(Problem problem, User user){
        return ProblemUser.builder()
                .problem(problem)
                .user(user)
                .problemStatus(ProblemStatus.PROCESS)
                .unsolvedCnt(0)
                .build();
    }

    public void changeStatus(ProblemStatus problemStatus){
        if(problemStatus == ProblemStatus.PROCESS){
            this.problemStatus = ProblemStatus.COMPLETE;
        }
    }

    public void addUnsolved(){
        this.unsolvedCnt++;
    }


}
