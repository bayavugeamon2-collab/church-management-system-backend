package com.auca.church.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Parish;
import com.auca.church.repository.ParishRepository;

@Service
public class ParishService {

    private final ParishRepository parishRepository;

    public ParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    public Parish save(Parish parish) {
        return parishRepository.save(parish);
    }

    public List<Parish> findAll() {
        return parishRepository.findAll();
    }

    public Optional<Parish> findById(UUID id) {
        return parishRepository.findById(id);
    }

    public String update(UUID id, Parish body) {
        Optional<Parish> opt = parishRepository.findById(id);
        if (opt.isEmpty()) {
            return "The parish with that id does not exist";
        }
        Parish existing = opt.get();
        existing.setParishNumber(body.getParishNumber());
        existing.setName(body.getName());
        parishRepository.save(existing);
        return "Parish updated successfully";
    }

    public String delete(UUID id) {
        if (!parishRepository.existsById(id)) {
            return "The parish with that id does not exist";
        }
        parishRepository.deleteById(id);
        return "Parish deleted successfully";
    }

    // C2
    public List<Parish> findFreeParishes() {
        return parishRepository.findFreeParishes();
    }
}