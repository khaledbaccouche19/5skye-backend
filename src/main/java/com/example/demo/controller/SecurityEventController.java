package com.example.demo.controller;

import com.example.demo.entities.SecurityEvent;
import com.example.demo.service.SecurityEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-events")
@CrossOrigin
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    public SecurityEventController(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @PostMapping
    public ResponseEntity<SecurityEvent> create(@RequestBody SecurityEvent event) {
        return ResponseEntity.status(201).body(securityEventService.save(event));
    }

    @GetMapping
    public List<SecurityEvent> getAll() {
        return securityEventService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityEvent> getById(@PathVariable Long id) {
        SecurityEvent event = securityEventService.findById(id);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityEvent> update(@PathVariable Long id, @RequestBody SecurityEvent updated) {
        SecurityEvent event = securityEventService.update(id, updated);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        securityEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
