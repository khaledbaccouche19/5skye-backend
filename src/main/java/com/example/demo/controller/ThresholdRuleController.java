package com.example.demo.controller;

import com.example.demo.dto.threshold.CreateThresholdRuleDTO;
import com.example.demo.dto.threshold.ThresholdRuleDTO;
import com.example.demo.service.ThresholdRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thresholds")
@RequiredArgsConstructor
public class ThresholdRuleController {

    private final ThresholdRuleService thresholdRuleService;

    @GetMapping
    public ResponseEntity<List<ThresholdRuleDTO>> getAllThresholdRules() {
        List<ThresholdRuleDTO> thresholdRules = thresholdRuleService.getAllThresholdRules();
        return ResponseEntity.ok(thresholdRules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThresholdRuleDTO> getThresholdRuleById(@PathVariable Long id) {
        ThresholdRuleDTO thresholdRule = thresholdRuleService.getThresholdRuleById(id);
        return ResponseEntity.ok(thresholdRule);
    }

    @GetMapping("/tower/{towerId}")
    public ResponseEntity<List<ThresholdRuleDTO>> getThresholdRulesByTowerId(@PathVariable Long towerId) {
        List<ThresholdRuleDTO> thresholdRules = thresholdRuleService.getThresholdRulesByTowerId(towerId);
        return ResponseEntity.ok(thresholdRules);
    }

    @PostMapping
    public ResponseEntity<ThresholdRuleDTO> createThresholdRule(@RequestBody CreateThresholdRuleDTO createThresholdRuleDTO) {
        ThresholdRuleDTO createdThresholdRule = thresholdRuleService.createThresholdRule(createThresholdRuleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdThresholdRule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThresholdRuleDTO> updateThresholdRule(
            @PathVariable Long id,
            @RequestBody CreateThresholdRuleDTO updateThresholdRuleDTO) {
        ThresholdRuleDTO updatedThresholdRule = thresholdRuleService.updateThresholdRule(id, updateThresholdRuleDTO);
        return ResponseEntity.ok(updatedThresholdRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThresholdRule(@PathVariable Long id) {
        thresholdRuleService.deleteThresholdRule(id);
        return ResponseEntity.noContent().build();
    }
}
