package com.example.demo.controller;

import com.example.demo.entities.DisplayMetrics;
import com.example.demo.service.DisplayMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/display-metrics")
@CrossOrigin
public class DisplayMetricsController {

    private final DisplayMetricsService displayMetricsService;

    public DisplayMetricsController(DisplayMetricsService displayMetricsService) {
        this.displayMetricsService = displayMetricsService;
    }

    @PostMapping
    public ResponseEntity<DisplayMetrics> create(@RequestBody DisplayMetrics metric) {
        return ResponseEntity.status(201).body(displayMetricsService.save(metric));
    }

    @GetMapping
    public List<DisplayMetrics> getAll() {
        return displayMetricsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisplayMetrics> getById(@PathVariable Long id) {
        DisplayMetrics metric = displayMetricsService.findById(id);
        return metric != null ? ResponseEntity.ok(metric) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisplayMetrics> update(@PathVariable Long id, @RequestBody DisplayMetrics updated) {
        DisplayMetrics metric = displayMetricsService.update(id, updated);
        return metric != null ? ResponseEntity.ok(metric) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        displayMetricsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
