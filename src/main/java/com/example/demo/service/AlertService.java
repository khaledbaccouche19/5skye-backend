package com.example.demo.service;

import com.example.demo.entities.Alert;
import com.example.demo.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> findAll() {
        return alertRepository.findAll();
    }

    public Alert findById(Long id) {
        return alertRepository.findById(id).orElse(null);
    }

    public Alert update(Long id, Alert updatedAlert) {
        return alertRepository.findById(id).map(existing -> {
            existing.setMessage(updatedAlert.getMessage());
            existing.setSeverity(updatedAlert.getSeverity());
            existing.setStatus(updatedAlert.getStatus());
            existing.setSource(updatedAlert.getSource());
            existing.setType(updatedAlert.getType());
            existing.setTower(updatedAlert.getTower());
            return alertRepository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        alertRepository.deleteById(id);
    }
}
