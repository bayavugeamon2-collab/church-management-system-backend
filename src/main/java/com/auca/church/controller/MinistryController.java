package com.auca.church.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auca.church.domain.Ministry;
import com.auca.church.service.MinistryService;

@RestController
@RequestMapping("/api/ministries")
public class MinistryController {

    private final MinistryService ministryService;

    public MinistryController(MinistryService ministryService) {
        this.ministryService = ministryService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ministry ministry) {
        ministryService.save(ministry);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ministry saved successfully");
    }

    @GetMapping("/all")
    public List<Ministry> getAll() {
        return ministryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var opt = ministryService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The ministry with that id does not exist");
        }
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Ministry body) {
        String message = ministryService.update(id, body);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = ministryService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    // C3
    @GetMapping("/unused")
    public List<Ministry> unused() {
        return ministryService.findUnused();
    }
}