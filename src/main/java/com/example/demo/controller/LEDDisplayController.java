package com.example.demo.controller;

import com.example.demo.dto.systems.LEDDisplayDTO;
import com.example.demo.dto.systems.CreateLEDDisplayDTO;
import com.example.demo.service.LEDDisplayService;
import com.example.demo.dto.mapper.LEDDisplayMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/led-displays")
@CrossOrigin
public class LEDDisplayController {
    private final LEDDisplayService ledDisplayService;
    private final LEDDisplayMapper ledDisplayMapper;
    public LEDDisplayController(LEDDisplayService ledDisplayService, LEDDisplayMapper ledDisplayMapper) {
        this.ledDisplayService = ledDisplayService;
        this.ledDisplayMapper = ledDisplayMapper;
    }

    @PostMapping
    public ResponseEntity<LEDDisplayDTO> create(@RequestBody @Valid CreateLEDDisplayDTO dto) {
        var entity = ledDisplayMapper.toEntity(dto);
        var saved = ledDisplayService.save(entity);
        LEDDisplayDTO created = ledDisplayMapper.toDto(saved);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public List<LEDDisplayDTO> getAll() {
        return ledDisplayMapper.toDtoList(ledDisplayService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LEDDisplayDTO> getById(@PathVariable Long id) {
        LEDDisplayDTO display = ledDisplayMapper.toDto(ledDisplayService.findById(id));
        return display != null ? ResponseEntity.ok(display) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LEDDisplayDTO> update(@PathVariable Long id, @RequestBody @Valid LEDDisplayDTO updatedDto) {
        var entity = ledDisplayMapper.toEntity(updatedDto);
        var updated = ledDisplayService.update(id, entity);
        LEDDisplayDTO dto = ledDisplayMapper.toDto(updated);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ledDisplayService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
