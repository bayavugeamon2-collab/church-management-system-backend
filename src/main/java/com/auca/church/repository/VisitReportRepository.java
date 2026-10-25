package com.auca.church.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.VisitReport;

@Repository
public interface VisitReportRepository extends JpaRepository<VisitReport, UUID> {

    // E2
    @Query("SELECT r FROM VisitReport r WHERE r.visitation.member.memberNumber = :memberNumber ORDER BY r.writtenDate DESC")
    List<VisitReport> findByMemberNumber(@Param("memberNumber") String memberNumber);

    // E3
    boolean existsByVisitationId(UUID visitationId);
}