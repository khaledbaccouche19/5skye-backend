package com.example.demo.controller;

import com.example.demo.entities.PowerMetrics;
import com.example.demo.service.PowerMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/power-metrics")

@CrossOrigin
public class PowerMetricsController {

    private final PowerMetricsService powerMetricsService;

    public PowerMetricsController(PowerMetricsService powerMetricsService) {
        this.powerMetricsService = powerMetricsService;
    }

    @PostMapping
    public ResponseEntity<PowerMetrics> create(@RequestBody PowerMetrics metrics) {
        return ResponseEntity.status(201).body(powerMetricsService.save(metrics));
    }

    @GetMapping
    public List<PowerMetrics> getAll() {
        return powerMetricsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PowerMetrics> getById(@PathVariable Long id) {
        PowerMetrics metric = powerMetricsService.findById(id);
        return metric != null ? ResponseEntity.ok(metric) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PowerMetrics> update(@PathVariable Long id, @RequestBody PowerMetrics updated) {
        PowerMetrics metric = powerMetricsService.update(id, updated);
        return metric != null ? ResponseEntity.ok(metric) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        powerMetricsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
