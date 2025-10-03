package com.example.demo.controller;

import com.example.demo.dto.alert.CreateAlertDTO;
import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.alert.AlertSummaryDTO;
import com.example.demo.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<AlertDTO>> getAllAlerts() {
        List<AlertDTO> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<AlertSummaryDTO>> getRecentAlerts(@RequestParam(defaultValue = "5") int limit) {
        List<AlertSummaryDTO> alerts = alertService.getRecentAlerts(limit);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertDTO> getAlertById(@PathVariable Long id) {
        AlertDTO alert = alertService.getAlertById(id);
        return ResponseEntity.ok(alert);
    }

    @GetMapping("/tower/{towerId}")
    public ResponseEntity<List<AlertDTO>> getAlertsByTowerId(@PathVariable Long towerId) {
        List<AlertDTO> alerts = alertService.getAlertsByTowerId(towerId);
        return ResponseEntity.ok(alerts);
    }

    @PostMapping
    public ResponseEntity<AlertDTO> createAlert(@RequestBody CreateAlertDTO createAlertDTO) {
        AlertDTO createdAlert = alertService.createAlert(createAlertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertDTO> updateAlert(
            @PathVariable Long id,
            @RequestBody CreateAlertDTO updateAlertDTO) {
        AlertDTO updatedAlert = alertService.updateAlert(id, updateAlertDTO);
        return ResponseEntity.ok(updatedAlert);
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<AlertDTO> resolveAlert(@PathVariable Long id) {
        AlertDTO resolvedAlert = alertService.resolveAlert(id);
        return ResponseEntity.ok(resolvedAlert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}
