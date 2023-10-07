package com.lito.core.problem.domain;

import com.lito.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "FAQ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE FAQ SET status = INACTIVE WHERE id = ?")
@Builder
public class Faq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(length = 250)
    private String question;

    @Column(length = 1000)
    private String answer;

    public static Faq createFaq(String question, String answer){
        return Faq.builder()
                .question(question)
                .answer(answer)
                .build();
    }

    public void setProblem(Problem problem){
        this.problem = problem;
        this.problem.getFaqs().add(this);
    }
}
