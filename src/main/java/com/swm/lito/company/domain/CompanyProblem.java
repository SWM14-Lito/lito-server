package com.swm.lito.company.domain;

import com.swm.lito.common.entity.BaseEntity;
import com.swm.lito.company.enums.*;
import com.swm.lito.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COMPANY_PROBLEM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE COMPANY_PROBLEM SET status = INACTIVE WHERE company_problem_id = ?")
@Builder
public class CompanyProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_problem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Company company;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    private int price;

    @Column(columnDefinition = "TEXT")
    private String confirmation;

    private String title;

    private LocalDateTime interviewedAt;

    private String mood;

    private String review;

    private int likeCnt;

    private int dislikeCnt;

    private int viewCnt;

    @Enumerated(EnumType.STRING)
    private UploadStatus uploadStatus;

    private String rejectReason;

}
