package com.auca.church.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auca.church.domain.Visitation;
import com.auca.church.domain.VisitationStatus;
import com.auca.church.service.VisitationService;

@RestController
@RequestMapping("/api/visitations")
public class VisitationController {

    private final VisitationService visitationService;

    public VisitationController(VisitationService visitationService) {
        this.visitationService = visitationService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Visitation visitation) {
        visitationService.save(visitation);
        return ResponseEntity.status(HttpStatus.CREATED).body("Visitation saved successfully");
    }

    @GetMapping("/all")
    public List<Visitation> getAll() {
        return visitationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var opt = visitationService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The visitation with that id does not exist");
        }
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Visitation body) {
        String message = visitationService.update(id, body);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = visitationService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    // B2
    @GetMapping("/by-status")
    public List<Visitation> byStatus(@RequestParam VisitationStatus status) {
        return visitationService.findByStatus(status);
    }

    // B3
    @GetMapping("/between")
    public List<Visitation> between(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return visitationService.findBetween(startDate, endDate);
    }
}