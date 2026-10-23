package com.auca.church.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auca.church.domain.Pastor;
import com.auca.church.service.PastorService;

@RestController
@RequestMapping("/api/pastors")
public class PastorController {

    private final PastorService pastorService;

    public PastorController(PastorService pastorService) {
        this.pastorService = pastorService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Pastor pastor) {
        pastorService.save(pastor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pastor saved successfully");
    }

    @GetMapping("/all")
    public List<Pastor> getAll() {
        return pastorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var opt = pastorService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The pastor with that id does not exist");
        }
        return ResponseEntity.ok(opt.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Pastor body) {
        String message = pastorService.update(id, body);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = pastorService.delete(id);
        if (message.contains("does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
