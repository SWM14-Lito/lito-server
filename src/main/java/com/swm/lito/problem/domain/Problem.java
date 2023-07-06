package com.swm.lito.problem.domain;

import com.swm.lito.common.entity.BaseEntity;
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
@SQLDelete(sql = "UPDATE PROBLEM SET status = INACTIVE WHERE problem_id = ?")
@Builder
public class Problem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_category_id")
    private SubjectCategory subjectCategory;

    @Column(length = 250)
    private String question;

    @Column(length = 250)
    private String answer;

    @Column(length = 10)
    private String keyword;

    //DB 등록여부
    private boolean registered;

    @OneToMany(mappedBy = "problem")
    @Builder.Default
    private List<Faq> faqs = new ArrayList<>();


}
