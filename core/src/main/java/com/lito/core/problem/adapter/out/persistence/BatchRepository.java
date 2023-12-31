package com.lito.core.problem.adapter.out.persistence;

import com.lito.core.problem.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, String> {

    Optional<Batch> findTopByRequestDateOrderByCreatedAtDesc(LocalDate requestDate);
}
