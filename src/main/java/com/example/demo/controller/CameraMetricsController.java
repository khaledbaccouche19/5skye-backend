package com.example.demo.controller;

import com.example.demo.entities.CameraMetrics;
import com.example.demo.service.CameraMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camera-metrics")
@CrossOrigin
public class CameraMetricsController {

    private final CameraMetricsService cameraMetricsService;

    public CameraMetricsController(CameraMetricsService cameraMetricsService) {
        this.cameraMetricsService = cameraMetricsService;
    }

    @PostMapping
    public ResponseEntity<CameraMetrics> create(@RequestBody CameraMetrics metrics) {
        return ResponseEntity.status(201).body(cameraMetricsService.save(metrics));
    }

    @GetMapping
    public List<CameraMetrics> getAll() {
        return cameraMetricsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CameraMetrics> getById(@PathVariable Long id) {
        CameraMetrics metrics = cameraMetricsService.findById(id);
        return metrics != null ? ResponseEntity.ok(metrics) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CameraMetrics> update(@PathVariable Long id, @RequestBody CameraMetrics updated) {
        CameraMetrics metrics = cameraMetricsService.update(id, updated);
        return metrics != null ? ResponseEntity.ok(metrics) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cameraMetricsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
