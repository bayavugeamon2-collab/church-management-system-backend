package com.auca.church.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Ministry;
import com.auca.church.repository.MinistryRepository;

@Service
public class MinistryService {

    private final MinistryRepository ministryRepository;

    public MinistryService(MinistryRepository ministryRepository) {
        this.ministryRepository = ministryRepository;
    }

    public Ministry save(Ministry ministry) {
        return ministryRepository.save(ministry);
    }

    public List<Ministry> findAll() {
        return ministryRepository.findAll();
    }

    public Optional<Ministry> findById(UUID id) {
        return ministryRepository.findById(id);
    }

    public String update(UUID id, Ministry body) {
        Optional<Ministry> opt = ministryRepository.findById(id);
        if (opt.isEmpty()) {
            return "The ministry with that id does not exist";
        }
        Ministry existing = opt.get();
        existing.setName(body.getName());
        ministryRepository.save(existing);
        return "Ministry updated successfully";
    }

    public String delete(UUID id) {
        if (!ministryRepository.existsById(id)) {
            return "The ministry with that id does not exist";
        }
        ministryRepository.deleteById(id);
        return "Ministry deleted successfully";
    }

    // C3
    public List<Ministry> findUnused() {
        return ministryRepository.findUnusedMinistries();
    }
}