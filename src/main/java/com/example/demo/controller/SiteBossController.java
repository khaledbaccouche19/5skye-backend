package com.example.demo.controller;

import com.example.demo.service.SiteBossService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/siteboss")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class SiteBossController {

    private final SiteBossService siteBossService;

    /**
     * Pull fresh data from SiteBoss device
     */
    @PostMapping("/pull")
    public ResponseEntity<Map<String, Object>> pullData() {
        try {
            log.info("API request to pull SiteBoss data");
            Map<String, Object> result = siteBossService.pullSiteBossData();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("❌ Error in SiteBoss pull endpoint", e);
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * Pull fresh data from SiteBoss device asynchronously
     */
    @PostMapping("/pull-async")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> pullDataAsync() {
        return siteBossService.pullSiteBossDataAsync()
            .thenApply(result -> {
                log.info("Async API request to pull SiteBoss data completed");
                return ResponseEntity.ok(result);
            })
            .exceptionally(throwable -> {
                log.error("Error in async SiteBoss pull endpoint", throwable);
                return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", throwable.getMessage()));
            });
    }

    /**
     * Get the latest SiteBoss data without pulling
     */
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestData() {
        try {
            log.info("📊 API request to get latest SiteBoss data");
            Map<String, Object> result = siteBossService.getLatestSiteBossData();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error in SiteBoss latest data endpoint", e);
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * Pull data for a specific tower using its stored credentials
     */
    @PostMapping("/pull/{towerId}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> pullForTower(@PathVariable Long towerId,
                                                                               @RequestParam String host,
                                                                               @RequestParam String username,
                                                                               @RequestParam String password) {
        return siteBossService.pullForTowerAsync(towerId, host, username, password)
            .thenApply(ResponseEntity::ok)
            .exceptionally(ex -> ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "error", ex.getMessage())));
    }

    /**
     * Get latest cached SiteBoss data for a specific tower
     */
    @GetMapping("/latest/{towerId}")
    public ResponseEntity<Map<String, Object>> latestForTower(@PathVariable Long towerId) {
        Map<String, Object> result = siteBossService.getLatestForTower(towerId);
        return ResponseEntity.ok(result);
    }

    /**
     * Health check for SiteBoss integration
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        try {
            Map<String, Object> result = siteBossService.getLatestSiteBossData();
            boolean isHealthy = (Boolean) result.getOrDefault("success", false);
            
            Map<String, Object> healthResponse = Map.of(
                "status", isHealthy ? "UP" : "DOWN",
                "service", "SiteBoss Integration",
                "timestamp", System.currentTimeMillis()
            );
            
            return ResponseEntity.ok(healthResponse);
        } catch (Exception e) {
            log.error("❌ SiteBoss health check failed", e);
            return ResponseEntity.ok(Map.of(
                "status", "DOWN",
                "service", "SiteBoss Integration",
                "error", e.getMessage(),
                "timestamp", System.currentTimeMillis()
            ));
        }
    }
}
