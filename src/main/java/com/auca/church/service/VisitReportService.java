package com.auca.church.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Visitation;
import com.auca.church.domain.VisitationStatus;
import com.auca.church.domain.VisitReport;
import com.auca.church.repository.VisitationRepository;
import com.auca.church.repository.VisitReportRepository;

@Service
public class VisitReportService {

    private final VisitReportRepository visitReportRepository;
    private final VisitationRepository visitationRepository;

    public VisitReportService(VisitReportRepository visitReportRepository,
            VisitationRepository visitationRepository) {
        this.visitReportRepository = visitReportRepository;
        this.visitationRepository = visitationRepository;
    }

    // E1
    public String save(UUID visitationId, VisitReport report) {
        Optional<Visitation> opt = visitationRepository.findById(visitationId);
        if (opt.isEmpty()) {
            return "The visitation with that id does not exist";
        }

        Visitation visitation = opt.get();

        if (visitation.getStatus() != VisitationStatus.VISITED) {
            return "Visit reports are only for VISITED visitations";
        }

        report.setVisitation(visitation);
        report.setWrittenDate(LocalDate.now());
        visitReportRepository.save(report);
        return "Visit report saved successfully";
    }

    // E2
    public List<VisitReport> findByMemberNumber(String memberNumber) {
        return visitReportRepository.findByMemberNumber(memberNumber);
    }

    // E3
    public String delete(UUID id) {
        if (!visitReportRepository.existsById(id)) {
            return "The visit report with that id does not exist";
        }
        visitReportRepository.deleteById(id);
        return "Visit report deleted successfully";
    }
}