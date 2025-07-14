package com.example.demo.controller;

import com.example.demo.dto.tower.TowerDetailDTO;
import com.example.demo.dto.tower.CreateTowerDTO;
import com.example.demo.service.TowerService;
import com.example.demo.dto.mapper.TowerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/towers")
@CrossOrigin
public class TowerController {
    private final TowerService towerService;
    private final TowerMapper towerMapper;
    public TowerController(TowerService towerService, TowerMapper towerMapper) {
        this.towerService = towerService;
        this.towerMapper = towerMapper;
    }

    @PostMapping
    public ResponseEntity<TowerDetailDTO> create(@RequestBody @Valid CreateTowerDTO dto) {
        var tower = towerMapper.toEntity(dto);
        var saved = towerService.save(tower);
        TowerDetailDTO created = towerMapper.toDetailDto(saved);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<TowerDetailDTO> getAll() {
        return towerMapper.toDetailDtoList(towerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TowerDetailDTO> getById(@PathVariable Long id) {
        TowerDetailDTO tower = towerMapper.toDetailDto(towerService.findById(id));
        return tower != null ? ResponseEntity.ok(tower) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TowerDetailDTO> update(@PathVariable Long id, @RequestBody @Valid CreateTowerDTO updatedTower) {
        var tower = towerMapper.toEntity(updatedTower);
        var updated = towerService.update(id, tower);
        TowerDetailDTO towerDto = towerMapper.toDetailDto(updated);
        return towerDto != null ? ResponseEntity.ok(towerDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        towerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
