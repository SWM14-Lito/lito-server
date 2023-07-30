package com.swm.lito.batch.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, String> {

    Optional<Batch> findByRequestDate(LocalDate requestDate);
}
