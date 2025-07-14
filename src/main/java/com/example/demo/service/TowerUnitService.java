package com.example.demo.service;

import com.example.demo.entities.TowerUnit;
import com.example.demo.repositories.TowerUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TowerUnitService {

    private final TowerUnitRepository towerUnitRepository;

    @Autowired
    public TowerUnitService(TowerUnitRepository towerUnitRepository) {
        this.towerUnitRepository = towerUnitRepository;
    }

    public TowerUnit save(TowerUnit unit) {
        return towerUnitRepository.save(unit);
    }

    public List<TowerUnit> findAll() {
        return towerUnitRepository.findAll();
    }

    public TowerUnit findById(Long id) {
        return towerUnitRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        towerUnitRepository.deleteById(id);
    }

    public TowerUnit update(Long id, TowerUnit updatedUnit) {
        return towerUnitRepository.findById(id).map(existing -> {
            existing.setUnitType(updatedUnit.getUnitType());
            existing.setHeight(updatedUnit.getHeight());
            existing.setWeight(updatedUnit.getWeight());
            existing.setDiameter(updatedUnit.getDiameter());
            existing.setStatus(updatedUnit.getStatus());
            existing.setStackPosition(updatedUnit.getStackPosition());
            existing.setSerialNumber(updatedUnit.getSerialNumber());
            existing.setManufacturer(updatedUnit.getManufacturer());
            existing.setModel(updatedUnit.getModel());
            existing.setDescription(updatedUnit.getDescription());
            existing.setInstallationDate(updatedUnit.getInstallationDate());
            existing.setInstallationNotes(updatedUnit.getInstallationNotes());
            existing.setIsRemovable(updatedUnit.getIsRemovable());
            existing.setColor(updatedUnit.getColor());
            existing.setMaterial(updatedUnit.getMaterial());
            existing.setMaxLoadCapacity(updatedUnit.getMaxLoadCapacity());
            existing.setWeatherResistance(updatedUnit.getWeatherResistance());
            existing.setCertifications(updatedUnit.getCertifications());
            return towerUnitRepository.save(existing);
        }).orElse(null);
    }
}
