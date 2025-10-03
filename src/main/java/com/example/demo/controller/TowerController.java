package com.example.demo.controller;

import com.example.demo.dto.tower.CreateTowerDTO;
import com.example.demo.dto.tower.TowerDTO  ;
import com.example.demo.dto.tower.TowerSummaryDTO;
import com.example.demo.service.TowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/towers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
public class TowerController {

    private final TowerService towerService;

    @GetMapping
    public ResponseEntity<List<TowerDTO>> getAllTowers() {
        List<TowerDTO> towers = towerService.getAllTowers();
        return ResponseEntity.ok(towers);
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<TowerSummaryDTO>> getAllTowerSummaries() {
        List<TowerSummaryDTO> towerSummaries = towerService.getAllTowerSummaries();
        return ResponseEntity.ok(towerSummaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TowerDTO> getTowerById(@PathVariable Long id) {
        TowerDTO tower = towerService.getTowerById(id);
        return ResponseEntity.ok(tower);
    }

    @PostMapping
    public ResponseEntity<TowerDTO> createTower(@Valid @RequestBody CreateTowerDTO createTowerDTO) {
        TowerDTO createdTower = towerService.createTower(createTowerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTower);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TowerDTO> updateTower(
            @PathVariable Long id,
            @Valid @RequestBody CreateTowerDTO updateTowerDTO) {
        TowerDTO updatedTower = towerService.updateTower(id, updateTowerDTO);
        return ResponseEntity.ok(updatedTower);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTower(@PathVariable Long id) {
        towerService.deleteTower(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/with-info")
    public ResponseEntity<String> deleteTowerWithInfo(@PathVariable Long id) {
        // Get dependencies info before deletion
        String dependencies = towerService.getTowerDependenciesInfo(id);
        
        // Perform deletion
        towerService.deleteTower(id);
        
        return ResponseEntity.ok("Tower deleted successfully. Deleted dependencies: " + dependencies);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<TowerDTO> softDeleteTower(@PathVariable Long id) {
        TowerDTO deactivatedTower = towerService.softDeleteTower(id);
        return ResponseEntity.ok(deactivatedTower);
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<TowerDTO> reactivateTower(@PathVariable Long id) {
        TowerDTO reactivatedTower = towerService.reactivateTower(id);
        return ResponseEntity.ok(reactivatedTower);
    }

    @PutMapping("/{id}/3d-model")
    public ResponseEntity<TowerDTO> updateTower3DModel(
            @PathVariable Long id,
            @RequestBody String model3dPath) {
        TowerDTO updatedTower = towerService.updateTower3DModel(id, model3dPath);
        return ResponseEntity.ok(updatedTower);
    }

    @GetMapping("/{id}/3d-model")
    public ResponseEntity<String> getTower3DModel(@PathVariable Long id) {
        String model3dPath = towerService.getTower3DModel(id);
        return ResponseEntity.ok(model3dPath);
    }

    @GetMapping("/{id}/can-delete")
    public ResponseEntity<Boolean> canDeleteTower(@PathVariable Long id) {
        boolean canDelete = towerService.canDeleteTower(id);
        return ResponseEntity.ok(canDelete);
    }

    @GetMapping("/{id}/dependencies")
    public ResponseEntity<String> getTowerDependencies(@PathVariable Long id) {
        String dependencies = towerService.getTowerDependenciesInfo(id);
        return ResponseEntity.ok(dependencies);
    }

    // Data cleanup and validation endpoints
    @PostMapping("/cleanup")
    public ResponseEntity<String> cleanupInvalidData() {
        String report = towerService.cleanupInvalidData();
        return ResponseEntity.ok(report);
    }
    
    @DeleteMapping("/{id}/invalid")
    public ResponseEntity<String> deleteInvalidTower(@PathVariable Long id) {
        try {
            towerService.deleteInvalidTower(id);
            return ResponseEntity.ok("Tower with ID " + id + " deleted due to invalid data");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/validation/status")
    public ResponseEntity<String> getValidationStatus() {
        List<TowerDTO> allTowers = towerService.getAllTowers();
        StringBuilder report = new StringBuilder();
        int validCount = 0;
        int invalidCount = 0;
        
        for (TowerDTO tower : allTowers) {
            if (tower.getLatitude() >= -90.0 && tower.getLatitude() <= 90.0 &&
                tower.getLongitude() >= -180.0 && tower.getLongitude() <= 180.0) {
                validCount++;
            } else {
                invalidCount++;
                report.append("Invalid Tower ID ").append(tower.getId())
                      .append(": lat=").append(tower.getLatitude())
                      .append(", lon=").append(tower.getLongitude()).append("\n");
            }
        }
        
        report.insert(0, "Validation Report:\n");
        report.append("Valid towers: ").append(validCount).append("\n");
        report.append("Invalid towers: ").append(invalidCount).append("\n");
        
        return ResponseEntity.ok(report.toString());
    }
    
    /**
     * Simple pull test endpoint for frontend
     */
    @GetMapping("/pull-test")
    public ResponseEntity<Map<String, Object>> pullTest() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test data retrieval
            List<TowerDTO> towers = towerService.getAllTowers();
            List<TowerSummaryDTO> summaries = towerService.getAllTowerSummaries();
            
            // Test individual tower retrieval
            TowerDTO sampleTower = null;
            if (!towers.isEmpty()) {
                sampleTower = towerService.getTowerById(towers.get(0).getId());
            }
            
            // Simple success/failure response
            if (towers != null && summaries != null && sampleTower != null) {
                response.put("status", "SUCCESSFUL");
            } else {
                response.put("status", "NOT SUCCESSFUL");
            }
            
        } catch (Exception e) {
            response.put("status", "NOT SUCCESSFUL");
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/telemetry/live")
    public ResponseEntity<Object> getTowerTelemetry(@PathVariable Long id) {
        try {
            return towerService.getTowerTelemetry(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch telemetry data: " + e.getMessage()));
        }
    }
}
