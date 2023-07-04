package com.swm.lito.problem.domain;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.problem.domain.enums.Category;
import com.swm.lito.problem.domain.enums.Subject;
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

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String answer;

    private String keyword;

    private boolean registered;

    @OneToMany(mappedBy = "problem")
    @Builder.Default
    private List<Faq> faqs = new ArrayList<>();


}
