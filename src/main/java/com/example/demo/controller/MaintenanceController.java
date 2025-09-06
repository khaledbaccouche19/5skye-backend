package com.example.demo.controller;

import com.example.demo.dto.maintenance.CreateMaintenanceDTO;
import com.example.demo.dto.maintenance.MaintenanceDTO;
import com.example.demo.entities.MaintenanceStatus;
import com.example.demo.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceDTO>> getAllMaintenance() {
        List<MaintenanceDTO> maintenance = maintenanceService.getAllMaintenance();
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id) {
        MaintenanceDTO maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/tower/{towerId}")
    public ResponseEntity<List<MaintenanceDTO>> getMaintenanceByTowerId(@PathVariable Long towerId) {
        List<MaintenanceDTO> maintenance = maintenanceService.getMaintenanceByTowerId(towerId);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceDTO>> getMaintenanceByStatus(@PathVariable MaintenanceStatus status) {
        List<MaintenanceDTO> maintenance = maintenanceService.getMaintenanceByStatus(status);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/tower/{towerId}/status/{status}")
    public ResponseEntity<List<MaintenanceDTO>> getMaintenanceByTowerIdAndStatus(
            @PathVariable Long towerId, 
            @PathVariable MaintenanceStatus status) {
        List<MaintenanceDTO> maintenance = maintenanceService.getMaintenanceByTowerIdAndStatus(towerId, status);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<MaintenanceDTO>> getOverdueMaintenance() {
        List<MaintenanceDTO> maintenance = maintenanceService.getOverdueMaintenance();
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MaintenanceDTO>> getUpcomingRecurringMaintenance() {
        List<MaintenanceDTO> maintenance = maintenanceService.getUpcomingRecurringMaintenance();
        return ResponseEntity.ok(maintenance);
    }

    @PostMapping
    public ResponseEntity<MaintenanceDTO> createMaintenance(@RequestBody CreateMaintenanceDTO createMaintenanceDTO) {
        MaintenanceDTO createdMaintenance = maintenanceService.createMaintenance(createMaintenanceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> updateMaintenance(
            @PathVariable Long id,
            @RequestBody CreateMaintenanceDTO updateMaintenanceDTO) {
        MaintenanceDTO updatedMaintenance = maintenanceService.updateMaintenance(id, updateMaintenanceDTO);
        return ResponseEntity.ok(updatedMaintenance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tower/{towerId}/count/{status}")
    public ResponseEntity<Long> getMaintenanceCountByTowerIdAndStatus(
            @PathVariable Long towerId, 
            @PathVariable MaintenanceStatus status) {
        Long count = maintenanceService.getMaintenanceCountByTowerIdAndStatus(towerId, status);
        return ResponseEntity.ok(count);
    }
}
