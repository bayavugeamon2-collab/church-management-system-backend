package com.auca.church.service;

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

    public Object save(Visitation visitation) {
        // for A2 basic save - will be improved in D1
        if (visitation.getStatus() == null) {
            visitation.setStatus(VisitationStatus.REQUESTED);
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
        // status is only changed through the status endpoint (D2)
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
}
