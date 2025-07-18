package com.example.demo.service;

import com.example.demo.entities.PowerSystem;
import com.example.demo.repositories.PowerSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerSystemService {

    private final PowerSystemRepository repository;

    @Autowired
    public PowerSystemService(PowerSystemRepository repository) {
        this.repository = repository;
    }

    public PowerSystem save(PowerSystem system) {
        return repository.save(system);
    }

    public List<PowerSystem> findAll() {
        return repository.findAll();
    }

    public PowerSystem findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PowerSystem update(Long id, PowerSystem updated) {
        return repository.findById(id).map(existing -> {
            existing.setVoltage(updated.getVoltage());
            existing.setCurrent(updated.getCurrent());
            existing.setMaxOutput(updated.getMaxOutput());
            existing.setStatus(updated.getStatus());
            existing.setManufacturer(updated.getManufacturer());
            existing.setModel(updated.getModel());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setPhases(updated.getPhases());
            existing.setFrequency(updated.getFrequency());
            existing.setEfficiency(updated.getEfficiency());
            existing.setInputVoltageRange(updated.getInputVoltageRange());
            existing.setOutputVoltageRange(updated.getOutputVoltageRange());
            existing.setPowerFactor(updated.getPowerFactor());
            existing.setTotalHarmonicDistortion(updated.getTotalHarmonicDistortion());
            existing.setRackUnits(updated.getRackUnits());
            existing.setWeight(updated.getWeight());
            existing.setDimensions(updated.getDimensions());
            existing.setOperatingTemperature(updated.getOperatingTemperature());
            existing.setCertifications(updated.getCertifications());
            existing.setInstallationDate(updated.getInstallationDate());
            existing.setLastMaintenanceDate(updated.getLastMaintenanceDate());
            existing.setMaintenanceNotes(updated.getMaintenanceNotes());
            return repository.save(existing);
        }).orElse(null);
    }
}
