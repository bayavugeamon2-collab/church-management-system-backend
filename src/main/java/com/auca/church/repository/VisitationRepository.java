package com.auca.church.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Visitation;
import com.auca.church.domain.VisitationStatus;

@Repository
public interface VisitationRepository extends JpaRepository<Visitation, UUID> {

    // B2
    List<Visitation> findByStatusOrderByVisitDateAsc(VisitationStatus status);

    // B3
    List<Visitation> findByVisitDateBetweenOrderByVisitDateAsc(LocalDate start, LocalDate end);
}