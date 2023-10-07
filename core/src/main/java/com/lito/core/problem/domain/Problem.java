package com.lito.core.problem.domain;

import com.lito.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "PROBLEM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE PROBLEM SET status = INACTIVE WHERE id = ?")
@Builder
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_category_id")
    private SubjectCategory subjectCategory;

    @Column(length = 250)
    private String question;

    @Column(length = 1000)
    private String answer;

    @Column(length = 100)
    private String keyword;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Faq> faqs = new ArrayList<>();

    public static Problem createProblem(Subject subject, SubjectCategory subjectCategory, String question,
                                        String answer, String keyword){
        return Problem.builder()
                .subject(subject)
                .subjectCategory(subjectCategory)
                .question(question)
                .answer(answer)
                .keyword(keyword)
                .build();
    }
}
