package com.example.demo.service;

import com.example.demo.entities.PowerMetrics;
import com.example.demo.repositories.PowerMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerMetricsService {

    private final PowerMetricsRepository repository;

    @Autowired
    public PowerMetricsService(PowerMetricsRepository repository) {
        this.repository = repository;
    }

    public PowerMetrics save(PowerMetrics metrics) {
        return repository.save(metrics);
    }

    public List<PowerMetrics> findAll() {
        return repository.findAll();
    }

    public PowerMetrics findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PowerMetrics update(Long id, PowerMetrics updated) {
        return repository.findById(id).map(existing -> {
            existing.setCurrentConsumption(updated.getCurrentConsumption());
            existing.setVoltage(updated.getVoltage());
            existing.setCurrent(updated.getCurrent());
            existing.setBatteryLevel(updated.getBatteryLevel());
            existing.setTemperature(updated.getTemperature());
            existing.setTimestamp(updated.getTimestamp());
            existing.setPowerFactor(updated.getPowerFactor());
            existing.setFrequency(updated.getFrequency());
            existing.setEfficiency(updated.getEfficiency());
            existing.setTotalHarmonicDistortion(updated.getTotalHarmonicDistortion());
            existing.setPeakDemand(updated.getPeakDemand());
            existing.setEnergyConsumed(updated.getEnergyConsumed());
            existing.setCarbonFootprint(updated.getCarbonFootprint());
            existing.setLoadProfile(updated.getLoadProfile());
            existing.setPowerQuality(updated.getPowerQuality());
            existing.setAlarmStatus(updated.getAlarmStatus());
            existing.setPowerSystem(updated.getPowerSystem());
            return repository.save(existing);
        }).orElse(null);
    }
}
