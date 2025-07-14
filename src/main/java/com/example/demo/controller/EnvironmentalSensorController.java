package com.example.demo.controller;

import com.example.demo.entities.EnvironmentalSensor;
import com.example.demo.service.EnvironmentalSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environmental-sensors")
@CrossOrigin
public class EnvironmentalSensorController {

    private final EnvironmentalSensorService environmentalSensorService;

    public EnvironmentalSensorController(EnvironmentalSensorService environmentalSensorService) {
        this.environmentalSensorService = environmentalSensorService;
    }

    @PostMapping
    public ResponseEntity<EnvironmentalSensor> create(@RequestBody EnvironmentalSensor sensor) {
        return ResponseEntity.status(201).body(environmentalSensorService.save(sensor));
    }

    @GetMapping
    public List<EnvironmentalSensor> getAll() {
        return environmentalSensorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentalSensor> getById(@PathVariable Long id) {
        EnvironmentalSensor sensor = environmentalSensorService.findById(id);
        return sensor != null ? ResponseEntity.ok(sensor) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentalSensor> update(@PathVariable Long id, @RequestBody EnvironmentalSensor updated) {
        EnvironmentalSensor sensor = environmentalSensorService.update(id, updated);
        return sensor != null ? ResponseEntity.ok(sensor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        environmentalSensorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
