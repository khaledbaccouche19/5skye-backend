package com.example.demo.service;

import com.example.demo.entities.Tower;
import com.example.demo.repositories.TowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class SiteBossPullScheduler {

    private static final Logger log = LoggerFactory.getLogger(SiteBossPullScheduler.class);

    private final SiteBossService siteBossService;
    private final TowerRepository towerRepository;
    private final AtomicBoolean pullInProgress = new AtomicBoolean(false);

    @Value("${siteboss.scheduler.enabled:true}")
    private boolean schedulerEnabled;

    public SiteBossPullScheduler(SiteBossService siteBossService, TowerRepository towerRepository) {
        this.siteBossService = siteBossService;
        this.towerRepository = towerRepository;
    }

    // Run every 30 seconds by default; configurable via property
    @Scheduled(fixedDelayString = "${siteboss.scheduler.delay-ms:30000}", initialDelayString = "${siteboss.scheduler.initial-delay-ms:2000}")
    public void scheduledPull() {
        log.info("🔄 SiteBoss scheduler triggered - enabled: {}", schedulerEnabled);
        
        if (!schedulerEnabled) {
            log.info("❌ SiteBoss scheduler is disabled, skipping");
            return;
        }

        if (!pullInProgress.compareAndSet(false, true)) {
            // Previous pull still running; skip to avoid overlap
            log.info("⏳ Previous SiteBoss pull still running, skipping");
            return;
        }

        // Set a timeout to reset the pullInProgress flag if it gets stuck
        CompletableFuture.delayedExecutor(90, java.util.concurrent.TimeUnit.SECONDS)
                .execute(() -> {
                    if (pullInProgress.get()) {
                        log.warn("⚠️ SiteBoss pull timeout - resetting pullInProgress flag");
                        pullInProgress.set(false);
                    }
                });

        try {
            List<Tower> towers = towerRepository.findAll();
            log.info("🏗️ Found {} towers total", towers.size());
            
            List<CompletableFuture<?>> tasks = new ArrayList<>();
            int enabledCount = 0;
            
            for (Tower tower : towers) {
                if (Boolean.TRUE.equals(tower.getSitebossEnabled())
                        && tower.getSitebossHost() != null && !tower.getSitebossHost().isBlank()
                        && tower.getSitebossUsername() != null && tower.getSitebossPassword() != null) {
                    enabledCount++;
                    log.info("🚀 Starting scheduled pull for tower {} ({} - {})", 
                            tower.getId(), tower.getName(), tower.getSitebossHost());
                    
                    CompletableFuture<?> cf = siteBossService.pullForTowerAsync(
                            tower.getId(),
                            tower.getSitebossHost(),
                            tower.getSitebossUsername(),
                            tower.getSitebossPassword()
                    ).thenAccept(result -> {
                        if (result != null && result.containsKey("success") && !(Boolean) result.get("success")) {
                            log.warn("SiteBoss pull failed for tower {}: {}", tower.getId(), result.get("error"));
                        }
                    }).exceptionally(ex -> {
                        log.warn("SiteBoss pull failed for tower {}: {}", tower.getId(), ex.getMessage());
                        return null;
                    });
                    tasks.add(cf);
                }
            }
            
            log.info("📊 SiteBoss enabled towers: {}/{}", enabledCount, towers.size());
            
            if (tasks.isEmpty()) {
                log.info("❌ No towers with SiteBoss enabled found");
                pullInProgress.set(false);
                return;
            }
            
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
                    .whenComplete((v, err) -> {
                        pullInProgress.set(false);
                        if (err != null) {
                            log.error("❌ SiteBoss scheduled pull completed with error: {}", err.getMessage());
                        } else {
                            log.info("✅ SiteBoss scheduled pull completed for {} towers (check individual results above)", tasks.size());
                        }
                    });
        } catch (Exception e) {
            pullInProgress.set(false);
            log.error("❌ SiteBoss scheduled pull threw exception: {}", e.getMessage(), e);
        }
    }
}


