package com.example.demo.controller;

import com.example.demo.entities.EquipmentMetrics;
import com.example.demo.service.EquipmentMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-metrics")
@CrossOrigin
public class EquipmentMetricsController {

    private final EquipmentMetricsService equipmentMetricsService;

    public EquipmentMetricsController(EquipmentMetricsService equipmentMetricsService) {
        this.equipmentMetricsService = equipmentMetricsService;
    }

    @PostMapping
    public ResponseEntity<EquipmentMetrics> create(@RequestBody EquipmentMetrics metrics) {
        return ResponseEntity.status(201).body(equipmentMetricsService.save(metrics));
    }

    @GetMapping
    public List<EquipmentMetrics> getAll() {
        return equipmentMetricsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentMetrics> getById(@PathVariable Long id) {
        EquipmentMetrics metrics = equipmentMetricsService.findById(id);
        return metrics != null ? ResponseEntity.ok(metrics) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentMetrics> update(@PathVariable Long id, @RequestBody EquipmentMetrics updated) {
        EquipmentMetrics metrics = equipmentMetricsService.update(id, updated);
        return metrics != null ? ResponseEntity.ok(metrics) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentMetricsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
