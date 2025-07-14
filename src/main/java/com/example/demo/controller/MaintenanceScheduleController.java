package com.example.demo.controller;

import com.example.demo.entities.MaintenanceSchedule;
import com.example.demo.service.MaintenanceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-schedules")
@CrossOrigin
public class MaintenanceScheduleController {

    private final MaintenanceScheduleService maintenanceScheduleService;

    public MaintenanceScheduleController(MaintenanceScheduleService maintenanceScheduleService) {
        this.maintenanceScheduleService = maintenanceScheduleService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceSchedule> create(@RequestBody MaintenanceSchedule schedule) {
        return ResponseEntity.status(201).body(maintenanceScheduleService.save(schedule));
    }

    @GetMapping
    public List<MaintenanceSchedule> getAll() {
        return maintenanceScheduleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceSchedule> getById(@PathVariable Long id) {
        MaintenanceSchedule schedule = maintenanceScheduleService.findById(id);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceSchedule> update(@PathVariable Long id, @RequestBody MaintenanceSchedule updated) {
        MaintenanceSchedule schedule = maintenanceScheduleService.update(id, updated);
        return schedule != null ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
