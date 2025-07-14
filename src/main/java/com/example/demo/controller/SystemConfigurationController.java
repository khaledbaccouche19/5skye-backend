package com.example.demo.controller;

import com.example.demo.entities.SystemConfiguration;
import com.example.demo.service.SystemConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-configurations")
@CrossOrigin
public class SystemConfigurationController {

    private final SystemConfigurationService systemConfigurationService;

    public SystemConfigurationController(SystemConfigurationService systemConfigurationService) {
        this.systemConfigurationService = systemConfigurationService;
    }

    @PostMapping
    public ResponseEntity<SystemConfiguration> create(@RequestBody SystemConfiguration config) {
        return ResponseEntity.status(201).body(systemConfigurationService.save(config));
    }

    @GetMapping
    public List<SystemConfiguration> getAll() {
        return systemConfigurationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemConfiguration> getById(@PathVariable Long id) {
        SystemConfiguration config = systemConfigurationService.findById(id);
        return config != null ? ResponseEntity.ok(config) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemConfiguration> update(@PathVariable Long id, @RequestBody SystemConfiguration updated) {
        SystemConfiguration config = systemConfigurationService.update(id, updated);
        return config != null ? ResponseEntity.ok(config) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        systemConfigurationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
