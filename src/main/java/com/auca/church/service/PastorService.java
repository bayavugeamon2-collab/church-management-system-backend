package com.auca.church.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Pastor;
import com.auca.church.repository.PastorRepository;

@Service
public class PastorService {

    private final PastorRepository pastorRepository;

    public PastorService(PastorRepository pastorRepository) {
        this.pastorRepository = pastorRepository;
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
}