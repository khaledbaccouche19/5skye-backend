package com.example.demo.controller;

import com.example.demo.entities.BatteryMetrics;
import com.example.demo.service.BatteryMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/battery-metrics")
@CrossOrigin
public class BatteryMetricsController {

    private final BatteryMetricsService batteryMetricsService;

    public BatteryMetricsController(BatteryMetricsService batteryMetricsService) {
        this.batteryMetricsService = batteryMetricsService;
    }

    @PostMapping
    public ResponseEntity<BatteryMetrics> create(@RequestBody BatteryMetrics metrics) {
        return ResponseEntity.status(201).body(batteryMetricsService.save(metrics));
    }

    @GetMapping
    public List<BatteryMetrics> getAll() {
        return batteryMetricsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatteryMetrics> getById(@PathVariable Long id) {
        BatteryMetrics metrics = batteryMetricsService.findById(id);
        return metrics != null ? ResponseEntity.ok(metrics) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatteryMetrics> update(@PathVariable Long id, @RequestBody BatteryMetrics updated) {
        BatteryMetrics metrics = batteryMetricsService.update(id, updated);
        return metrics != null ? ResponseEntity.ok(metrics) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        batteryMetricsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
