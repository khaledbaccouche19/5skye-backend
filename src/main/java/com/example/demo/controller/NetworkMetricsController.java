package com.example.demo.controller;

import com.example.demo.entities.NetworkMetrics;
import com.example.demo.service.NetworkMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network-metrics")
@CrossOrigin
public class NetworkMetricsController {

    private final NetworkMetricsService networkMetricsService;

    public NetworkMetricsController(NetworkMetricsService networkMetricsService) {
        this.networkMetricsService = networkMetricsService;
    }

    @PostMapping
    public ResponseEntity<NetworkMetrics> create(@RequestBody NetworkMetrics metric) {
        return ResponseEntity.status(201).body(networkMetricsService.save(metric));
    }

    @GetMapping
    public List<NetworkMetrics> getAll() {
        return networkMetricsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NetworkMetrics> getById(@PathVariable Long id) {
        NetworkMetrics metric = networkMetricsService.findById(id);
        return metric != null ? ResponseEntity.ok(metric) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NetworkMetrics> update(@PathVariable Long id, @RequestBody NetworkMetrics updated) {
        NetworkMetrics metric = networkMetricsService.update(id, updated);
        return metric != null ? ResponseEntity.ok(metric) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        networkMetricsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
