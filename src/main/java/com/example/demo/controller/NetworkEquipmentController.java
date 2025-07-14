package com.example.demo.controller;

import com.example.demo.entities.NetworkEquipment;
import com.example.demo.service.NetworkEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network-equipment")
@CrossOrigin
public class NetworkEquipmentController {

    private final NetworkEquipmentService networkEquipmentService;

    public NetworkEquipmentController(NetworkEquipmentService networkEquipmentService) {
        this.networkEquipmentService = networkEquipmentService;
    }

    @PostMapping
    public ResponseEntity<NetworkEquipment> create(@RequestBody NetworkEquipment equipment) {
        return ResponseEntity.status(201).body(networkEquipmentService.save(equipment));
    }

    @GetMapping
    public List<NetworkEquipment> getAll() {
        return networkEquipmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NetworkEquipment> getById(@PathVariable Long id) {
        NetworkEquipment equipment = networkEquipmentService.findById(id);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NetworkEquipment> update(@PathVariable Long id, @RequestBody NetworkEquipment updated) {
        NetworkEquipment equipment = networkEquipmentService.update(id, updated);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        networkEquipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
