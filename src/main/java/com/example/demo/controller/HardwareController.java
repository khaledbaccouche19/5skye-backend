package com.example.demo.controller;

import com.example.demo.dto.hardware.CreateHardwareDTO;
import com.example.demo.dto.hardware.HardwareDTO;
import com.example.demo.dto.hardware.HardwareSummaryDTO;
import com.example.demo.service.HardwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hardware")
@RequiredArgsConstructor
public class HardwareController {

    private final HardwareService hardwareService;

    @GetMapping
    public ResponseEntity<List<HardwareDTO>> getAllHardware() {
        List<HardwareDTO> hardware = hardwareService.getAllHardware();
        return ResponseEntity.ok(hardware);
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<HardwareSummaryDTO>> getAllHardwareSummaries() {
        List<HardwareSummaryDTO> hardwareSummaries = hardwareService.getAllHardwareSummaries();
        return ResponseEntity.ok(hardwareSummaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardwareDTO> getHardwareById(@PathVariable Long id) {
        HardwareDTO hardware = hardwareService.getHardwareById(id);
        return ResponseEntity.ok(hardware);
    }

    @GetMapping("/tower/{towerId}")
    public ResponseEntity<List<HardwareDTO>> getHardwareByTowerId(@PathVariable Long towerId) {
        List<HardwareDTO> hardware = hardwareService.getHardwareByTowerId(towerId);
        return ResponseEntity.ok(hardware);
    }

    @PostMapping
    public ResponseEntity<HardwareDTO> createHardware(@RequestBody CreateHardwareDTO createHardwareDTO) {
        HardwareDTO createdHardware = hardwareService.createHardware(createHardwareDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHardware);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HardwareDTO> updateHardware(
            @PathVariable Long id,
            @RequestBody CreateHardwareDTO updateHardwareDTO) {
        HardwareDTO updatedHardware = hardwareService.updateHardware(id, updateHardwareDTO);
        return ResponseEntity.ok(updatedHardware);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHardware(@PathVariable Long id) {
        hardwareService.deleteHardware(id);
        return ResponseEntity.noContent().build();
    }
}
