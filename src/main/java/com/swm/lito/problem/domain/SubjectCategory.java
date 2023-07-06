package com.swm.lito.problem.domain;

import com.swm.lito.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "SUBJECT_CATEGORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Where(clause = "status='ACTIVE'")
@SQLDelete(sql = "UPDATE SUBJECT_CATEGORY SET status = INACTIVE WHERE subject_category_id = ?")
@Builder
public class SubjectCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_category_id")
    private Long id;

    @Column(name = "subject_category_name")
    private String subjectCategoryName;
}
