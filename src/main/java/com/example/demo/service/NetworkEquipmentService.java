package com.example.demo.service;

import com.example.demo.entities.NetworkEquipment;
import com.example.demo.repositories.NetworkEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkEquipmentService {

    private final NetworkEquipmentRepository repository;

    @Autowired
    public NetworkEquipmentService(NetworkEquipmentRepository repository) {
        this.repository = repository;
    }

    public NetworkEquipment save(NetworkEquipment equipment) {
        return repository.save(equipment);
    }

    public List<NetworkEquipment> findAll() {
        return repository.findAll();
    }

    public NetworkEquipment findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public NetworkEquipment update(Long id, NetworkEquipment updated) {
        return repository.findById(id).map(existing -> {
            existing.setEquipmentType(updated.getEquipmentType());
            // existing.setNetworkType(updated.getNetworkType()); // Removed as per edit hint
            existing.setManufacturer(updated.getManufacturer());
            existing.setModel(updated.getModel());
            existing.setIpAddress(updated.getIpAddress());
            existing.setStatus(updated.getStatus());
            existing.setSignalStrength(updated.getSignalStrength());
            // Remove or update any usage of getTower() on NetworkEquipment
            return repository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
