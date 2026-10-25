package com.auca.church.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Visitation;
import com.auca.church.domain.VisitationStatus;

@Repository
public interface VisitationRepository extends JpaRepository<Visitation, UUID> {

    // B2
    List<Visitation> findByStatusOrderByVisitDateAsc(VisitationStatus status);

    // B3
    List<Visitation> findByVisitDateBetweenOrderByVisitDateAsc(LocalDate start, LocalDate end);

    // D1
    boolean existsByPastorIdAndVisitDateAndStatusNot(UUID pastorId, LocalDate visitDate, VisitationStatus status);

    // F1
    @Query("SELECT v.status, COUNT(v) FROM Visitation v GROUP BY v.status")
    List<Object[]> countByStatus();

    // F2
    @Modifying
    @Query("UPDATE Visitation v SET v.status = com.auca.church.domain.VisitationStatus.CALLED_OFF " +
            "WHERE v.pastor.id = :pastorId AND v.visitDate = :date AND v.status <> com.auca.church.domain.VisitationStatus.VISITED")
    int cancelDay(@Param("pastorId") UUID pastorId, @Param("date") LocalDate date);
}