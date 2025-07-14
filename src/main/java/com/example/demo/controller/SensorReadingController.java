package com.example.demo.controller;

import com.example.demo.entities.SensorReading;
import com.example.demo.service.SensorReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor-readings")
@CrossOrigin
public class SensorReadingController {

    private final SensorReadingService sensorReadingService;

    public SensorReadingController(SensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @PostMapping
    public ResponseEntity<SensorReading> create(@RequestBody SensorReading reading) {
        return ResponseEntity.status(201).body(sensorReadingService.save(reading));
    }

    @GetMapping
    public List<SensorReading> getAll() {
        return sensorReadingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorReading> getById(@PathVariable Long id) {
        SensorReading reading = sensorReadingService.findById(id);
        return reading != null ? ResponseEntity.ok(reading) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorReading> update(@PathVariable Long id, @RequestBody SensorReading updated) {
        SensorReading reading = sensorReadingService.update(id, updated);
        return reading != null ? ResponseEntity.ok(reading) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sensorReadingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
