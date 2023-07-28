package com.swm.lito.batch.domain;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "BATCH")
@Builder
public class Batch {

    @Id
    private String targetId;

    private LocalDate requestDate;

    public static Batch from(String targetId, LocalDate date){
        return Batch.builder()
                .targetId(targetId)
                .requestDate(date)
                .build();
    }
}
