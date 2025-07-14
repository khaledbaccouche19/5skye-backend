package com.example.demo.service;

import com.example.demo.entities.Tower;
import com.example.demo.repositories.TowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TowerService {

    private final TowerRepository towerRepository;

    @Autowired
    public TowerService(TowerRepository towerRepository) {
        this.towerRepository = towerRepository;
    }

    public Tower save(Tower tower) {
        return towerRepository.save(tower);
    }

    public List<Tower> findAll() {
        return towerRepository.findAll();
    }

    public Tower findById(Long id) {
        return towerRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        towerRepository.deleteById(id);
    }

    public Tower update(Long id, Tower updatedTower) {
        return towerRepository.findById(id).map(existing -> {
            existing.setTowerName(updatedTower.getTowerName());
            existing.setLocation(updatedTower.getLocation());
            existing.setLatitude(updatedTower.getLatitude());
            existing.setLongitude(updatedTower.getLongitude());
            existing.setStatus(updatedTower.getStatus());
            existing.setInstallationDate(updatedTower.getInstallationDate());
            existing.setMunicipality(updatedTower.getMunicipality());
            existing.setAddress(updatedTower.getAddress());
            existing.setSerialNumber(updatedTower.getSerialNumber());
            existing.setManufacturer(updatedTower.getManufacturer());
            existing.setModel(updatedTower.getModel());
            existing.setTotalHeight(updatedTower.getTotalHeight());
            existing.setTotalWeight(updatedTower.getTotalWeight());
            existing.setIsFullyLoaded(updatedTower.getIsFullyLoaded());
            existing.setDescription(updatedTower.getDescription());
            existing.setContactPerson(updatedTower.getContactPerson());
            existing.setContactPhone(updatedTower.getContactPhone());
            existing.setContactEmail(updatedTower.getContactEmail());
            existing.setLastMaintenanceDate(updatedTower.getLastMaintenanceDate());
            existing.setNextMaintenanceDate(updatedTower.getNextMaintenanceDate());
            existing.setWarrantyExpiryDate(updatedTower.getWarrantyExpiryDate());
            existing.setInstallationCompany(updatedTower.getInstallationCompany());
            existing.setOperatorCompany(updatedTower.getOperatorCompany());
            return towerRepository.save(existing);
        }).orElse(null);
    }
}
