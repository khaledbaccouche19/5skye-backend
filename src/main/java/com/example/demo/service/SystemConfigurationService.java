package com.example.demo.service;

import com.example.demo.entities.SystemConfiguration;
import com.example.demo.repositories.SystemConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigurationService {

    private final SystemConfigurationRepository repository;

    @Autowired
    public SystemConfigurationService(SystemConfigurationRepository repository) {
        this.repository = repository;
    }

    public SystemConfiguration save(SystemConfiguration config) {
        return repository.save(config);
    }

    public List<SystemConfiguration> findAll() {
        return repository.findAll();
    }

    public SystemConfiguration findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SystemConfiguration update(Long id, SystemConfiguration updated) {
        return repository.findById(id).map(existing -> {
            existing.setKey(updated.getKey());
            existing.setValue(updated.getValue());
            existing.setDescription(updated.getDescription());
            existing.setTower(updated.getTower());
            return repository.save(existing);
        }).orElse(null);
    }
}
