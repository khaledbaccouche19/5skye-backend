package com.example.demo.controller;

import com.example.demo.repositories.TowerRepository;
import com.example.demo.repositories.AlertRepository;
import com.example.demo.repositories.HardwareRepository;
import com.example.demo.repositories.TelemetryDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthCheckController {

    private final TowerRepository towerRepository;
    private final AlertRepository alertRepository;
    private final HardwareRepository hardwareRepository;
    private final TelemetryDataRepository telemetryDataRepository;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Simple connection test endpoint for frontend
     */
    @GetMapping("/connection-test")
    public ResponseEntity<Map<String, Object>> connectionTest() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test database connection
            boolean dbConnected = testDatabaseConnection();
            
            // Test basic repository operations
            boolean reposWorking = testRepositories();
            
            // Simple success/failure response
            if (dbConnected && reposWorking) {
                response.put("status", "SUCCESSFUL");
            } else {
                response.put("status", "NOT SUCCESSFUL");
            }
            
        } catch (Exception e) {
            response.put("status", "NOT SUCCESSFUL");
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Simple ping endpoint
     */
    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESSFUL");
        return ResponseEntity.ok(response);
    }

    private boolean testDatabaseConnection() {
        try {
            // Test basic SQL query
            String result = jdbcTemplate.queryForObject("SELECT 'OK' as status", String.class);
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean testRepositories() {
        try {
            // Test basic repository operations
            towerRepository.count();
            alertRepository.count();
            hardwareRepository.count();
            telemetryDataRepository.count();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

