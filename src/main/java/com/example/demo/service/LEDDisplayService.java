package com.example.demo.service;

import com.example.demo.entities.LEDDisplay;
import com.example.demo.repositories.LEDDisplayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LEDDisplayService {

    private final LEDDisplayRepository repository;

    @Autowired
    public LEDDisplayService(LEDDisplayRepository repository) {
        this.repository = repository;
    }

    public LEDDisplay save(LEDDisplay display) {
        return repository.save(display);
    }

    public List<LEDDisplay> findAll() {
        return repository.findAll();
    }

    public LEDDisplay findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public LEDDisplay update(Long id, LEDDisplay updated) {
        return repository.findById(id).map(existing -> {
            existing.setDisplayName(updated.getDisplayName());
            existing.setPixelPitch(updated.getPixelPitch());
            existing.setBrightness(updated.getBrightness());
            existing.setResolution(updated.getResolution());
            existing.setStatus(updated.getStatus());
            existing.setIsActive(updated.getIsActive());
            existing.setManufacturer(updated.getManufacturer());
            existing.setModel(updated.getModel());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setLampType(updated.getLampType());
            existing.setLifespan(updated.getLifespan());
            existing.setColorCalibration(updated.getColorCalibration());
            existing.setGrayScale(updated.getGrayScale());
            existing.setBrightnessControl(updated.getBrightnessControl());
            existing.setModuleSize(updated.getModuleSize());
            existing.setRefreshFrequency(updated.getRefreshFrequency());
            existing.setModuleResolution(updated.getModuleResolution());
            existing.setDriveMethod(updated.getDriveMethod());
            existing.setModuleMaterial(updated.getModuleMaterial());
            existing.setModuleType(updated.getModuleType());
            existing.setPcbType(updated.getPcbType());
            existing.setPcbCoating(updated.getPcbCoating());
            existing.setPanelMaterial(updated.getPanelMaterial());
            existing.setPanelPaint(updated.getPanelPaint());
            existing.setProtectionLevel(updated.getProtectionLevel());
            existing.setViewingAngle(updated.getViewingAngle());
            existing.setMinimumViewingDistance(updated.getMinimumViewingDistance());
            existing.setOperatingTemperature(updated.getOperatingTemperature());
            existing.setStorageTemperature(updated.getStorageTemperature());
            existing.setOperatingHumidity(updated.getOperatingHumidity());
            existing.setPowerInput(updated.getPowerInput());
            existing.setPowerFrequency(updated.getPowerFrequency());
            existing.setAveragePower(updated.getAveragePower());
            existing.setMaximumPower(updated.getMaximumPower());
            existing.setCoolingMethod(updated.getCoolingMethod());
            existing.setMaxInputResolution(updated.getMaxInputResolution());
            existing.setVideoSources(updated.getVideoSources());
            existing.setBrightnessAdjustment(updated.getBrightnessAdjustment());
            existing.setDeadPixelMonitoring(updated.getDeadPixelMonitoring());
            existing.setModuleMonitoring(updated.getModuleMonitoring());
            existing.setTemperatureMonitoring(updated.getTemperatureMonitoring());
            existing.setSmokeMonitoring(updated.getSmokeMonitoring());
            existing.setVoltageMonitoring(updated.getVoltageMonitoring());
            existing.setSignalPathBackup(updated.getSignalPathBackup());
            existing.setSignalTransmission(updated.getSignalTransmission());
            existing.setStandbyScheduling(updated.getStandbyScheduling());
            existing.setPowerScheduling(updated.getPowerScheduling());
            existing.setPowerSavingMode(updated.getPowerSavingMode());
            existing.setCertifications(updated.getCertifications());
            existing.setLedBrand(updated.getLedBrand());
            existing.setLedSurfaceColor(updated.getLedSurfaceColor());
            existing.setInstallationDate(updated.getInstallationDate());
            existing.setLastMaintenanceDate(updated.getLastMaintenanceDate());
            existing.setMaintenanceNotes(updated.getMaintenanceNotes());
            return repository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
