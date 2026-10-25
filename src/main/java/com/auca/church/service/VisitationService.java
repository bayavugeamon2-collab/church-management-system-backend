package com.auca.church.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Visitation;
import com.auca.church.domain.VisitationStatus;
import com.auca.church.repository.VisitationRepository;

@Service
public class VisitationService {

    private final VisitationRepository visitationRepository;

    public VisitationService(VisitationRepository visitationRepository) {
        this.visitationRepository = visitationRepository;
    }

    // D1 - Safe saving
    public Object save(Visitation visitation) {
        // date cannot be in the past
        if (visitation.getVisitDate() != null &&
                visitation.getVisitDate().isBefore(LocalDate.now())) {
            return "Visitation date cannot be in the past";
        }

        // always start as REQUESTED
        visitation.setStatus(VisitationStatus.REQUESTED);

        // check if pastor already has a visitation on that date (not CALLED_OFF)
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
        // status is only changed in D2 endpoint
        if (body.getMember() != null) {
            existing.setMember(body.getMember());
        }
        if (body.getPastor() != null) {
            existing.setPastor(body.getPastor());
        }
        visitationRepository.save(existing);
        return "Visitation updated successfully";
    }

    public String delete(UUID id) {
        if (!visitationRepository.existsById(id)) {
            return "The visitation with that id does not exist";
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

    // D2 - Status flow
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
        // VISITED and CALLED_OFF cannot change

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        visitation.setStatus(newStatus);
        visitationRepository.save(visitation);
        return "Visitation status updated successfully";
    }
}