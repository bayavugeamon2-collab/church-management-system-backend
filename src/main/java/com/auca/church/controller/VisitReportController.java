package com.auca.church.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auca.church.domain.VisitReport;
import com.auca.church.service.VisitReportService;

@RestController
@RequestMapping("/api/visit-reports")
public class VisitReportController {

    private final VisitReportService visitReportService;

    public VisitReportController(VisitReportService visitReportService) {
        this.visitReportService = visitReportService;
    }

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID visitationId,
            @RequestBody VisitReport report) {
        String message = visitReportService.save(visitationId, report);

        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.contains("only for VISITED")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // E2
    @GetMapping("/member/{memberNumber}")
    public List<VisitReport> byMember(@PathVariable String memberNumber) {
        return visitReportService.findByMemberNumber(memberNumber);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = visitReportService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}