package com.auca.church.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Ministry;
import com.auca.church.domain.Parish;
import com.auca.church.domain.Pastor;
import com.auca.church.repository.MinistryRepository;
import com.auca.church.repository.ParishRepository;
import com.auca.church.repository.PastorRepository;

@Service
public class PastorService {

    private final PastorRepository pastorRepository;
    private final MinistryRepository ministryRepository;
    private final ParishRepository parishRepository;

    public PastorService(PastorRepository pastorRepository,
            MinistryRepository ministryRepository,
            ParishRepository parishRepository) {
        this.pastorRepository = pastorRepository;
        this.ministryRepository = ministryRepository;
        this.parishRepository = parishRepository;
    }

    public Pastor save(Pastor pastor) {
        return pastorRepository.save(pastor);
    }

    public List<Pastor> findAll() {
        return pastorRepository.findAll();
    }

    public Optional<Pastor> findById(UUID id) {
        return pastorRepository.findById(id);
    }

    public String update(UUID id, Pastor body) {
        Optional<Pastor> opt = pastorRepository.findById(id);
        if (opt.isEmpty()) {
            return "The pastor with that id does not exist";
        }
        Pastor existing = opt.get();
        existing.setFirstName(body.getFirstName());
        existing.setLastName(body.getLastName());
        existing.setPhone(body.getPhone());
        if (body.getParish() != null) {
            existing.setParish(body.getParish());
        }
        pastorRepository.save(existing);
        return "Pastor updated successfully";
    }

    public String delete(UUID id) {
        if (!pastorRepository.existsById(id)) {
            return "The pastor with that id does not exist";
        }
        pastorRepository.deleteById(id);
        return "Pastor deleted successfully";
    }

    // C1
    public List<Pastor> findByMinistry(String name) {
        return pastorRepository.findByMinistryName(name);
    }

    // D3 - Add ministry to pastor
    public String addMinistry(UUID pastorId, UUID ministryId) {
        Optional<Pastor> pastorOpt = pastorRepository.findById(pastorId);
        if (pastorOpt.isEmpty()) {
            return "The pastor with that id does not exist";
        }

        Optional<Ministry> ministryOpt = ministryRepository.findById(ministryId);
        if (ministryOpt.isEmpty()) {
            return "That ministry does not exist";
        }

        Pastor pastor = pastorOpt.get();
        Ministry ministry = ministryOpt.get();

        if (pastor.getMinistries().contains(ministry)) {
            return "The pastor already has that ministry";
        }

        pastor.getMinistries().add(ministry);
        pastorRepository.save(pastor);
        return "Ministry added to the pastor";
    }

    // D4 - Move pastor to another parish
    public String changeParish(UUID pastorId, int parishNumber) {
        Optional<Pastor> pastorOpt = pastorRepository.findById(pastorId);
        if (pastorOpt.isEmpty()) {
            return "The pastor with that id does not exist";
        }

        Optional<Parish> parishOpt = parishRepository.findByParishNumber(parishNumber);
        if (parishOpt.isEmpty()) {
            return "The parish with that parishNumber does not exist";
        }

        Pastor pastor = pastorOpt.get();
        Parish newParish = parishOpt.get();

        // check if parish is taken by another pastor
        if (pastorRepository.existsByParishId(newParish.getId())) {
            if (pastor.getParish() == null || !pastor.getParish().getId().equals(newParish.getId())) {
                return "That parish is taken by another pastor";
            }
        }

        pastor.setParish(newParish);
        pastorRepository.save(pastor);
        return "Parish changed successfully";
    }
}