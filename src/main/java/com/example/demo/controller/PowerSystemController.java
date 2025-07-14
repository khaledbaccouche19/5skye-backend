package com.example.demo.controller;

import com.example.demo.dto.systems.PowerSystemDTO;
import com.example.demo.dto.systems.CreatePowerSystemDTO;
import com.example.demo.service.PowerSystemService;
import com.example.demo.dto.mapper.PowerSystemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/power-systems")
@CrossOrigin
public class PowerSystemController {
    private final PowerSystemService powerSystemService;
    private final PowerSystemMapper powerSystemMapper;
    public PowerSystemController(PowerSystemService powerSystemService, PowerSystemMapper powerSystemMapper) {
        this.powerSystemService = powerSystemService;
        this.powerSystemMapper = powerSystemMapper;
    }

    @PostMapping
    public ResponseEntity<PowerSystemDTO> create(@RequestBody @Valid CreatePowerSystemDTO dto) {
        var entity = powerSystemMapper.toEntity(dto);
        var saved = powerSystemService.save(entity);
        PowerSystemDTO created = powerSystemMapper.toDto(saved);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<PowerSystemDTO> getAll() {
        return powerSystemMapper.toDtoList(powerSystemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PowerSystemDTO> getById(@PathVariable Long id) {
        PowerSystemDTO system = powerSystemMapper.toDto(powerSystemService.findById(id));
        return system != null ? ResponseEntity.ok(system) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PowerSystemDTO> update(@PathVariable Long id, @RequestBody @Valid PowerSystemDTO updatedDto) {
        var entity = powerSystemMapper.toEntity(updatedDto);
        var updated = powerSystemService.update(id, entity);
        PowerSystemDTO dto = powerSystemMapper.toDto(updated);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        powerSystemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
