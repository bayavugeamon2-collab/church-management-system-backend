package com.auca.church.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Visitation;
import com.auca.church.domain.VisitationStatus;
import com.auca.church.repository.VisitationRepository;
import com.auca.church.repository.VisitReportRepository;

@Service
public class VisitationService {

    private final VisitationRepository visitationRepository;
    private final VisitReportRepository visitReportRepository;

    public VisitationService(VisitationRepository visitationRepository,
            VisitReportRepository visitReportRepository) {
        this.visitationRepository = visitationRepository;
        this.visitReportRepository = visitReportRepository;
    }

    // D1 - Safe saving
    public Object save(Visitation visitation) {
        if (visitation.getVisitDate() != null &&
                visitation.getVisitDate().isBefore(LocalDate.now())) {
            return "Visitation date cannot be in the past";
        }

        visitation.setStatus(VisitationStatus.REQUESTED);

        if (visitation.getPastor() != null && visitation.getVisitDate() != null) {
            boolean booked = visitationRepository.existsByPastorIdAndVisitDateAndStatusNot(
                    visitation.getPastor().getId(),
                    visitation.getVisitDate(),
                    VisitationStatus.CALLED_OFF);

            if (booked) {
                return "Pastor already has a visitation on that date";
            }
        }

        return visitationRepository.save(visitation);
    }

    public List<Visitation> findAll() {
        return visitationRepository.findAll();
    }

    public Optional<Visitation> findById(UUID id) {
        return visitationRepository.findById(id);
    }

    public String update(UUID id, Visitation body) {
        Optional<Visitation> opt = visitationRepository.findById(id);
        if (opt.isEmpty()) {
            return "The visitation with that id does not exist";
        }
        Visitation existing = opt.get();
        existing.setVisitDate(body.getVisitDate());
        existing.setPurpose(body.getPurpose());
        if (body.getMember() != null) {
            existing.setMember(body.getMember());
        }
        if (body.getPastor() != null) {
            existing.setPastor(body.getPastor());
        }
        visitationRepository.save(existing);
        return "Visitation updated successfully";
    }

    // E3 - protect delete
    public String delete(UUID id) {
        if (!visitationRepository.existsById(id)) {
            return "The visitation with that id does not exist";
        }

        if (visitReportRepository.existsByVisitationId(id)) {
            return "That visitation has visit reports, delete them first";
        }

        visitationRepository.deleteById(id);
        return "Visitation deleted successfully";
    }

    // B2
    public List<Visitation> findByStatus(VisitationStatus status) {
        return visitationRepository.findByStatusOrderByVisitDateAsc(status);
    }

    // B3
    public List<Visitation> findBetween(LocalDate start, LocalDate end) {
        return visitationRepository.findByVisitDateBetweenOrderByVisitDateAsc(start, end);
    }

    // D2
    public String updateStatus(UUID id, VisitationStatus newStatus) {
        Optional<Visitation> opt = visitationRepository.findById(id);
        if (opt.isEmpty()) {
            return "The visitation with that id does not exist";
        }

        Visitation visitation = opt.get();
        VisitationStatus current = visitation.getStatus();

        boolean allowed = false;

        if (current == VisitationStatus.REQUESTED) {
            if (newStatus == VisitationStatus.PLANNED || newStatus == VisitationStatus.CALLED_OFF) {
                allowed = true;
            }
        } else if (current == VisitationStatus.PLANNED) {
            if (newStatus == VisitationStatus.VISITED || newStatus == VisitationStatus.CALLED_OFF) {
                allowed = true;
            }
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        visitation.setStatus(newStatus);
        visitationRepository.save(visitation);
        return "Visitation status updated successfully";
    }
}