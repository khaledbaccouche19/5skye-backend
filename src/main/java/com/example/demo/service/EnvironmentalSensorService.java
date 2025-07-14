package com.example.demo.service;

import com.example.demo.entities.EnvironmentalSensor;
import com.example.demo.repositories.EnvironmentalSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalSensorService {

    private final EnvironmentalSensorRepository repository;

    @Autowired
    public EnvironmentalSensorService(EnvironmentalSensorRepository repository) {
        this.repository = repository;
    }

    public EnvironmentalSensor save(EnvironmentalSensor sensor) {
        return repository.save(sensor);
    }

    public List<EnvironmentalSensor> findAll() {
        return repository.findAll();
    }

    public EnvironmentalSensor findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public EnvironmentalSensor update(Long id, EnvironmentalSensor updated) {
        return repository.findById(id).map(existing -> {
            existing.setSensorType(updated.getSensorType());
            existing.setLocation(updated.getLocation());
            existing.setStatus(updated.getStatus());
            existing.setCurrentValue(updated.getCurrentValue());
            existing.setMinThreshold(updated.getMinThreshold());
            existing.setMaxThreshold(updated.getMaxThreshold());
            existing.setManufacturer(updated.getManufacturer());
            existing.setModel(updated.getModel());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setSensorTechnology(updated.getSensorTechnology());
            existing.setMeasurementUnit(updated.getMeasurementUnit());
            existing.setMeasurementRange(updated.getMeasurementRange());
            existing.setAccuracy(updated.getAccuracy());
            existing.setResolution(updated.getResolution());
            existing.setResponseTime(updated.getResponseTime());
            existing.setOperatingTemperature(updated.getOperatingTemperature());
            existing.setOperatingHumidity(updated.getOperatingHumidity());
            existing.setPowerRequirement(updated.getPowerRequirement());
            existing.setOutputSignal(updated.getOutputSignal());
            existing.setCommunicationProtocol(updated.getCommunicationProtocol());
            existing.setCalibrationInterval(updated.getCalibrationInterval());
            existing.setLastCalibrationDate(updated.getLastCalibrationDate());
            existing.setNextCalibrationDate(updated.getNextCalibrationDate());
            existing.setCalibrationCertificate(updated.getCalibrationCertificate());
            existing.setInstallationMethod(updated.getInstallationMethod());
            existing.setCableLength(updated.getCableLength());
            existing.setEnclosureType(updated.getEnclosureType());
            existing.setProtectionRating(updated.getProtectionRating());
            existing.setCertifications(updated.getCertifications());
            existing.setWarrantyPeriod(updated.getWarrantyPeriod());
            existing.setMaintenanceRequirements(updated.getMaintenanceRequirements());
            existing.setTroubleshootingGuide(updated.getTroubleshootingGuide());
            existing.setSparePartsAvailable(updated.getSparePartsAvailable());
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
