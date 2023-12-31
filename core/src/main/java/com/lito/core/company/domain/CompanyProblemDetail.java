package com.lito.core.company.domain;

import com.lito.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "COMPANY_PROBLEM_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE COMPANY_PROBLEM_DETAIL SET status = INACTIVE WHERE id = ?")
@Builder
public class CompanyProblemDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_problem_id")
    private CompanyProblem companyProblem;

    @Column(length = 250)
    private String question;

    @Column(length = 1000)
    private String answer;


}
