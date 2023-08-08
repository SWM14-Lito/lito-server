package com.swm.lito.core.problem.domain;

import com.swm.lito.core.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "BATCH")
@Builder
public class Batch extends BaseEntity {

    @Id
    private String taskId;

    private LocalDate requestDate;

    public static Batch from(String taskId, LocalDate date){
        return Batch.builder()
                .taskId(taskId)
                .requestDate(date)
                .build();
    }
}
