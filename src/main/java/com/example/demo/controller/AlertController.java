package com.example.demo.controller;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.alert.CreateAlertDTO;
import com.example.demo.service.AlertService;
import com.example.demo.dto.mapper.AlertMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin
public class AlertController {
    private final AlertService alertService;
    private final AlertMapper alertMapper;
    public AlertController(AlertService alertService, AlertMapper alertMapper) {
        this.alertService = alertService;
        this.alertMapper = alertMapper;
    }

    @PostMapping
    public ResponseEntity<AlertDTO> create(@RequestBody @Valid CreateAlertDTO dto) {
        var entity = alertMapper.toEntity(dto);
        var saved = alertService.save(entity);
        AlertDTO created = alertMapper.toDto(saved);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<AlertDTO> getAll() {
        return alertMapper.toDtoList(alertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertDTO> getAlertById(@PathVariable Long id) {
        AlertDTO alert = alertMapper.toDto(alertService.findById(id));
        return alert != null
                ? ResponseEntity.ok(alert)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertDTO> updateAlert(@PathVariable Long id, @RequestBody AlertDTO updated) {
        var entity = alertMapper.toEntity(updated);
        var updatedEntity = alertService.update(id, entity);
        AlertDTO alertDto = alertMapper.toDto(updatedEntity);
        return alertDto != null ? ResponseEntity.ok(alertDto) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
