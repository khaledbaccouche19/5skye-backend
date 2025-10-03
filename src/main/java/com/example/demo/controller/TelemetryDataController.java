package com.example.demo.controller;

import com.example.demo.dto.telemetry.CreateTelemetryDataDTO;
import com.example.demo.dto.telemetry.TelemetryDataDTO;
import com.example.demo.service.TelemetryDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryDataController {

    private final TelemetryDataService telemetryDataService;

    @GetMapping
    public ResponseEntity<List<TelemetryDataDTO>> getAllTelemetryData() {
        List<TelemetryDataDTO> telemetryData = telemetryDataService.getAllTelemetryData();
        return ResponseEntity.ok(telemetryData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelemetryDataDTO> getTelemetryDataById(@PathVariable Long id) {
        TelemetryDataDTO telemetryData = telemetryDataService.getTelemetryDataById(id);
        return ResponseEntity.ok(telemetryData);
    }

    @GetMapping("/tower/{towerId}")
    public ResponseEntity<List<TelemetryDataDTO>> getTelemetryDataByTowerId(@PathVariable Long towerId) {
        List<TelemetryDataDTO> telemetryData = telemetryDataService.getTelemetryDataByTowerId(towerId);
        return ResponseEntity.ok(telemetryData);
    }

    @GetMapping("/tower/{towerId}/range")
    public ResponseEntity<List<TelemetryDataDTO>> getTelemetryDataByTowerIdAndTimeRange(
            @PathVariable Long towerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        List<TelemetryDataDTO> telemetryData = telemetryDataService.getTelemetryDataByTowerIdAndTimeRange(towerId, startTime, endTime);
        return ResponseEntity.ok(telemetryData);
    }

    @PostMapping
    public ResponseEntity<TelemetryDataDTO> createTelemetryData(@RequestBody CreateTelemetryDataDTO createTelemetryDataDTO) {
        TelemetryDataDTO createdTelemetryData = telemetryDataService.createTelemetryData(createTelemetryDataDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTelemetryData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelemetryDataDTO> updateTelemetryData(
            @PathVariable Long id,
            @RequestBody CreateTelemetryDataDTO updateTelemetryDataDTO) {
        TelemetryDataDTO updatedTelemetryData = telemetryDataService.updateTelemetryData(id, updateTelemetryDataDTO);
        return ResponseEntity.ok(updatedTelemetryData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelemetryData(@PathVariable Long id) {
        telemetryDataService.deleteTelemetryData(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tower/{towerId}")
    public ResponseEntity<Void> deleteTelemetryDataByTowerId(@PathVariable Long towerId) {
        telemetryDataService.deleteTelemetryDataByTowerId(towerId);
        return ResponseEntity.noContent().build();
    }
}
