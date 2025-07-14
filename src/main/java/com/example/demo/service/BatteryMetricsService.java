package com.example.demo.service;

import com.example.demo.entities.BatteryMetrics;
import com.example.demo.repositories.BatteryMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryMetricsService {

    private final BatteryMetricsRepository batteryMetricsRepository;

    @Autowired
    public BatteryMetricsService(BatteryMetricsRepository batteryMetricsRepository) {
        this.batteryMetricsRepository = batteryMetricsRepository;
    }

    public BatteryMetrics save(BatteryMetrics metrics) {
        return batteryMetricsRepository.save(metrics);
    }

    public List<BatteryMetrics> findAll() {
        return batteryMetricsRepository.findAll();
    }

    public BatteryMetrics findById(Long id) {
        return batteryMetricsRepository.findById(id).orElse(null);
    }

    public BatteryMetrics update(Long id, BatteryMetrics updated) {
        return batteryMetricsRepository.findById(id).map(existing -> {
            existing.setChargeLevel(updated.getChargeLevel());
            existing.setVoltage(updated.getVoltage());
            existing.setCurrent(updated.getCurrent());
            existing.setTemperature(updated.getTemperature());
            existing.setTimestamp(updated.getTimestamp());
            existing.setStateOfHealth(updated.getStateOfHealth());
            existing.setInternalResistance(updated.getInternalResistance());
            existing.setCycleCount(updated.getCycleCount());
            existing.setChargingState(updated.getChargingState());
            existing.setTimeToEmpty(updated.getTimeToEmpty());
            existing.setTimeToFull(updated.getTimeToFull());
            existing.setCellVoltage1(updated.getCellVoltage1());
            existing.setCellVoltage2(updated.getCellVoltage2());
            existing.setCellVoltage3(updated.getCellVoltage3());
            existing.setCellVoltage4(updated.getCellVoltage4());
            existing.setIsBalancing(updated.getIsBalancing());
            existing.setAlarmStatus(updated.getAlarmStatus());
            existing.setBattery(updated.getBattery());
            return batteryMetricsRepository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        batteryMetricsRepository.deleteById(id);
    }
}

