package com.example.demo.service;

import com.example.demo.entities.SecurityCamera;
import com.example.demo.repositories.SecurityCameraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityCameraService {

    private final SecurityCameraRepository repository;

    @Autowired
    public SecurityCameraService(SecurityCameraRepository repository) {
        this.repository = repository;
    }

    public SecurityCamera save(SecurityCamera camera) {
        return repository.save(camera);
    }

    public List<SecurityCamera> findAll() {
        return repository.findAll();
    }

    public SecurityCamera findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SecurityCamera update(Long id, SecurityCamera updated) {
        return repository.findById(id).map(existing -> {
            existing.setCameraName(updated.getCameraName());
            existing.setIpAddress(updated.getIpAddress());
            existing.setResolution(updated.getResolution());
            existing.setPanAngle(updated.getPanAngle());
            existing.setTiltAngle(updated.getTiltAngle());
            existing.setIsRecording(updated.getIsRecording());
            existing.setManufacturer(updated.getManufacturer());
            existing.setModel(updated.getModel());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setMacAddress(updated.getMacAddress());
            existing.setFirmwareVersion(updated.getFirmwareVersion());
            existing.setSensorType(updated.getSensorType());
            existing.setLensType(updated.getLensType());
            existing.setCompressionFormat(updated.getCompressionFormat());
            existing.setMaxFrameRate(updated.getMaxFrameRate());
            existing.setNightVisionCapability(updated.getNightVisionCapability());
            existing.setWeatherResistance(updated.getWeatherResistance());
            existing.setMountingType(updated.getMountingType());
            existing.setPowerRequirement(updated.getPowerRequirement());
            existing.setNetworkProtocols(updated.getNetworkProtocols());
            existing.setStreamingProtocols(updated.getStreamingProtocols());
            existing.setAudioCapability(updated.getAudioCapability());
            existing.setMotionDetection(updated.getMotionDetection());
            existing.setVideoAnalytics(updated.getVideoAnalytics());
            existing.setStorageCapacity(updated.getStorageCapacity());
            existing.setStorageType(updated.getStorageType());
            existing.setEncryptionSupport(updated.getEncryptionSupport());
            existing.setAccessControl(updated.getAccessControl());
            existing.setPanRange(updated.getPanRange());
            existing.setTiltRange(updated.getTiltRange());
            existing.setZoomRange(updated.getZoomRange());
            existing.setPresetPositions(updated.getPresetPositions());
            existing.setPatrolRoutes(updated.getPatrolRoutes());
            existing.setAlarmInputs(updated.getAlarmInputs());
            existing.setAlarmOutputs(updated.getAlarmOutputs());
            existing.setInstallationDate(updated.getInstallationDate());
            existing.setLastMaintenanceDate(updated.getLastMaintenanceDate());
            existing.setMaintenanceNotes(updated.getMaintenanceNotes());
            return repository.save(existing);
        }).orElse(null);
    }
}
