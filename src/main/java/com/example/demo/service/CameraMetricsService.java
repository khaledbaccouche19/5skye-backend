package com.example.demo.service;

import com.example.demo.entities.CameraMetrics;
import com.example.demo.repositories.CameraMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraMetricsService {

    private final CameraMetricsRepository cameraMetricsRepository;

    @Autowired
    public CameraMetricsService(CameraMetricsRepository cameraMetricsRepository) {
        this.cameraMetricsRepository = cameraMetricsRepository;
    }

    public CameraMetrics save(CameraMetrics metrics) {
        return cameraMetricsRepository.save(metrics);
    }

    public List<CameraMetrics> findAll() {
        return cameraMetricsRepository.findAll();
    }

    public CameraMetrics findById(Long id) {
        return cameraMetricsRepository.findById(id).orElse(null);
    }

    public CameraMetrics update(Long id, CameraMetrics updated) {
        return cameraMetricsRepository.findById(id).map(existing -> {
            existing.setTimestamp(updated.getTimestamp());
            existing.setStatus(updated.getStatus());
            existing.setCpuUsage(updated.getCpuUsage());
            existing.setMemoryUsage(updated.getMemoryUsage());
            existing.setTemperature(updated.getTemperature());
            existing.setBandwidth(updated.getBandwidth());
            existing.setFrameRate(updated.getFrameRate());
            existing.setImageQuality(updated.getImageQuality());
            existing.setNetworkLatency(updated.getNetworkLatency());
            existing.setStorageUsage(updated.getStorageUsage());
            existing.setCompressionRatio(updated.getCompressionRatio());
            existing.setBitrate(updated.getBitrate());
            existing.setStreamHealth(updated.getStreamHealth());
            existing.setErrorCount(updated.getErrorCount());
            existing.setUptime(updated.getUptime());
            existing.setFocusStatus(updated.getFocusStatus());
            existing.setExposureSettings(updated.getExposureSettings());
            existing.setWhiteBalanceSettings(updated.getWhiteBalanceSettings());
            existing.setNoiseLevel(updated.getNoiseLevel());
            existing.setMotionActivity(updated.getMotionActivity());
            existing.setAlertsTriggered(updated.getAlertsTriggered());
            existing.setCamera(updated.getCamera());
            return cameraMetricsRepository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        cameraMetricsRepository.deleteById(id);
    }
}
