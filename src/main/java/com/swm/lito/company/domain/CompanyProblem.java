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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_status")
    private InterviewStatus interviewStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "career_status")
    private CareerStatus careerStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_status")
    private ResultStatus resultStatus;

    private int price;

    @Column(columnDefinition = "TEXT")
    private String confirmation;

    @Column(length = 25)
    private String title;

    @Column(name = "interviewed_date")
    private LocalDateTime interviewedDate;

    @Column(length = 250)
    private String mood;

    @Column(length = 250)
    private String review;

    @Column(name = "like_cnt")
    private int likeCnt;

    @Column(name = "dislike_cnt")
    private int dislikeCnt;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_status")
    private UploadStatus uploadStatus;

    @Column(name = "reject_reason", length = 50)
    private String rejectReason;

}
