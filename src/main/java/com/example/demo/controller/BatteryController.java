package com.example.demo.controller;

import com.example.demo.dto.systems.BatteryDTO;
import com.example.demo.dto.systems.CreateBatteryDTO;
import com.example.demo.service.BatteryService;
import com.example.demo.dto.mapper.BatteryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/batteries")
@CrossOrigin
public class BatteryController {
    static {
        System.out.println("DEBUG: BatteryController loaded!");
    }
    private final BatteryService batteryService;
    private final BatteryMapper batteryMapper;
    public BatteryController(BatteryService batteryService, BatteryMapper batteryMapper) {
        this.batteryService = batteryService;
        this.batteryMapper = batteryMapper;
    }

    @PostMapping
    public ResponseEntity<BatteryDTO> create(@RequestBody @Valid CreateBatteryDTO dto) {
        var saved = batteryService.save(dto); // Pass DTO directly, service handles mapping
        BatteryDTO created = batteryMapper.toDto(saved);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<BatteryDTO> getAll() {
        return batteryService.getAllBatteryDTOs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatteryDTO> getById(@PathVariable Long id) {
        var entity = batteryService.findById(id);
        BatteryDTO battery = batteryMapper.toDto(entity);
        return battery != null ? ResponseEntity.ok(battery) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatteryDTO> update(@PathVariable Long id, @RequestBody @Valid BatteryDTO updated) {
        System.out.println("DEBUG: update() called");
        System.out.println("DEBUG: batteryId = " + updated.getBatteryId());
        System.out.println("DEBUG: batteryType = " + updated.getBatteryType());
        System.out.println("DEBUG: capacity = " + updated.getCapacity());
        System.out.println("DEBUG: voltage = " + updated.getVoltage());
        System.out.println("DEBUG: currentCharge = " + updated.getCurrentCharge());
        System.out.println("DEBUG: status = " + updated.getStatus());
        System.out.println("DEBUG: serialNumber = " + updated.getSerialNumber());
        System.out.println("DEBUG: installationDate = " + updated.getInstallationDate());
        var updatedEntity = batteryService.update(id, updated);
        BatteryDTO battery = updatedEntity != null ? batteryMapper.toDto(updatedEntity) : null;
        return battery != null ? ResponseEntity.ok(battery) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        batteryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
