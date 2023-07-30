package com.swm.lito.core.notification.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import com.swm.lito.core.company.domain.CompanyProblem;
import com.swm.lito.core.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "NOTIFICATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE NOTIFICATION SET status = INACTIVE WHERE id = ?")
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_problem_id")
    private CompanyProblem companyProblem;

    private String content;

    @Column(name = "is_read")
    private boolean isRead;
}
