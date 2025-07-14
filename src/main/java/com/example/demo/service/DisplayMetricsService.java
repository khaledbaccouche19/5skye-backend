package com.example.demo.service;

import com.example.demo.entities.DisplayMetrics;
import com.example.demo.repositories.DisplayMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayMetricsService {

    private final DisplayMetricsRepository repository;

    @Autowired
    public DisplayMetricsService(DisplayMetricsRepository repository) {
        this.repository = repository;
    }

    public DisplayMetrics save(DisplayMetrics metric) {
        return repository.save(metric);
    }

    public List<DisplayMetrics> findAll() {
        return repository.findAll();
    }

    public DisplayMetrics findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public DisplayMetrics update(Long id, DisplayMetrics updated) {
        return repository.findById(id).map(existing -> {
            existing.setTimestamp(updated.getTimestamp());
            existing.setStatus(updated.getStatus());
            existing.setBrightness(updated.getBrightness());
            existing.setTemperature(updated.getTemperature());
            existing.setPowerConsumption(updated.getPowerConsumption());
            existing.setVoltage(updated.getVoltage());
            existing.setCurrent(updated.getCurrent());
            existing.setPixelHealth(updated.getPixelHealth());
            existing.setModuleStatus(updated.getModuleStatus());
            existing.setColorAccuracy(updated.getColorAccuracy());
            existing.setRefreshRate(updated.getRefreshRate());
            existing.setErrorCount(updated.getErrorCount());
            existing.setUptime(updated.getUptime());
            existing.setNetworkLatency(updated.getNetworkLatency());
            existing.setSignalStrength(updated.getSignalStrength());
            existing.setContentPlayback(updated.getContentPlayback());
            existing.setSchedulerStatus(updated.getSchedulerStatus());
            existing.setFanSpeed(updated.getFanSpeed());
            existing.setHumidityLevel(updated.getHumidityLevel());
            existing.setAmbientLight(updated.getAmbientLight());
            existing.setViewingConditions(updated.getViewingConditions());
            existing.setMaintenanceAlerts(updated.getMaintenanceAlerts());
            existing.setDisplay(updated.getDisplay());
            return repository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
