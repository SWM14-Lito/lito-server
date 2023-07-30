package com.swm.lito.core.company.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import com.swm.lito.core.company.enums.CareerStatus;
import com.swm.lito.core.company.enums.InterviewStatus;
import com.swm.lito.core.company.enums.ResultStatus;
import com.swm.lito.core.company.enums.UploadStatus;
import com.swm.lito.core.user.domain.User;
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
@SQLDelete(sql = "UPDATE COMPANY_PROBLEM SET status = INACTIVE WHERE id = ?")
@Builder
public class CompanyProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(length = 100)
    private String title;

    @Column(name = "interviewed_date")
    private LocalDateTime interviewedDate;

    @Column(length = 1000)
    private String mood;

    @Column(length = 1000)
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

    @Column(name = "reject_reason", length = 200)
    private String rejectReason;

}
