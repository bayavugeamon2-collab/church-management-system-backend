package com.auca.church.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // D1
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Visitation visitation) {
        Object result = visitationService.save(visitation);

        if (result instanceof String) {
            String msg = (String) result;
            if (msg.contains("past")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
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

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = visitationService.delete(id);

        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.contains("visit reports")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
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

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id,
            @RequestParam VisitationStatus status) {
        String message = visitationService.updateStatus(id, status);

        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.contains("Cannot change")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.ok(message);
    }

    // F1
    @GetMapping("/stats/by-status")
    public List<Object[]> statsByStatus() {
        return visitationService.countByStatus();
    }

    // F2
    @PatchMapping("/cancel-day")
    public String cancelDay(@RequestParam UUID pastorId, @RequestParam String date) {
        return visitationService.cancelDay(pastorId, LocalDate.parse(date));
    }

    // Bonus
    @GetMapping("/page")
    public Page<Visitation> page(@RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort) {
        String[] sortParts = sort.split(",");
        String field = sortParts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return visitationService.findPage(pageable);
    }
}