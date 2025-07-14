package com.example.demo.controller;

import com.example.demo.entities.SecurityCamera;
import com.example.demo.service.SecurityCameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-cameras")
@CrossOrigin
public class SecurityCameraController {

    private final SecurityCameraService securityCameraService;

    public SecurityCameraController(SecurityCameraService securityCameraService) {
        this.securityCameraService = securityCameraService;
    }

    @PostMapping
    public ResponseEntity<SecurityCamera> create(@RequestBody SecurityCamera camera) {
        return ResponseEntity.status(201).body(securityCameraService.save(camera));
    }

    @GetMapping
    public List<SecurityCamera> getAll() {
        return securityCameraService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityCamera> getById(@PathVariable Long id) {
        SecurityCamera camera = securityCameraService.findById(id);
        return camera != null ? ResponseEntity.ok(camera) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityCamera> update(@PathVariable Long id, @RequestBody SecurityCamera updated) {
        SecurityCamera camera = securityCameraService.update(id, updated);
        return camera != null ? ResponseEntity.ok(camera) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        securityCameraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
