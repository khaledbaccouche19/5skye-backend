package com.example.demo.controller;

import com.example.demo.entities.TowerUnit;
import com.example.demo.service.TowerUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tower-units")
@CrossOrigin
public class TowerUnitController {

    private final TowerUnitService towerUnitService;

    public TowerUnitController(TowerUnitService towerUnitService) {
        this.towerUnitService = towerUnitService;
    }

    @PostMapping
    public ResponseEntity<TowerUnit> create(@RequestBody TowerUnit towerUnit) {
        return ResponseEntity.status(201).body(towerUnitService.save(towerUnit));
    }

    @GetMapping
    public List<TowerUnit> getAll() {
        return towerUnitService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TowerUnit> getById(@PathVariable Long id) {
        TowerUnit unit = towerUnitService.findById(id);
        return unit != null ? ResponseEntity.ok(unit) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TowerUnit> update(@PathVariable Long id, @RequestBody TowerUnit updatedUnit) {
        TowerUnit unit = towerUnitService.update(id, updatedUnit);
        return unit != null ? ResponseEntity.ok(unit) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        towerUnitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
