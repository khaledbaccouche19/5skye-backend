package com.example.demo.service;

import com.example.demo.dto.alert.CreateAlertDTO;
import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.alert.AlertSummaryDTO;
import com.example.demo.dto.mapper.AlertMapper;
import com.example.demo.entities.Alert;
import com.example.demo.repositories.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    public List<AlertDTO> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(alertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AlertDTO getAlertById(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found with id: " + id));
        return alertMapper.toDTO(alert);
    }

    public List<AlertDTO> getAlertsByTowerId(Long towerId) {
        return alertRepository.findByTowerId(towerId).stream()
                .map(alertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AlertSummaryDTO> getRecentAlerts(int limit) {
        return alertRepository.findTop10ByOrderByTimestampDesc().stream()
                .limit(limit)
                .map(alertMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public AlertDTO createAlert(CreateAlertDTO createAlertDTO) {
        Alert alert = alertMapper.toEntity(createAlertDTO);
        alert.setTimestamp(Instant.now());
        Alert savedAlert = alertRepository.save(alert);
        return alertMapper.toDTO(savedAlert);
    }

    public AlertDTO updateAlert(Long id, CreateAlertDTO updateAlertDTO) {
        Alert existingAlert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found with id: " + id));
        
        existingAlert.setMessage(updateAlertDTO.getMessage());
        existingAlert.setSeverity(alertMapper.toEntity(updateAlertDTO).getSeverity());
        existingAlert.setTowerId(updateAlertDTO.getTowerId());
        existingAlert.setTowerName(updateAlertDTO.getTowerName());
        existingAlert.setResolved(updateAlertDTO.getResolved());
        
        Alert updatedAlert = alertRepository.save(existingAlert);
        return alertMapper.toDTO(updatedAlert);
    }

    public AlertDTO resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found with id: " + id));
        alert.setResolved(true);
        Alert resolvedAlert = alertRepository.save(alert);
        return alertMapper.toDTO(resolvedAlert);
    }

    public void deleteAlert(Long id) {
        if (!alertRepository.existsById(id)) {
            throw new RuntimeException("Alert not found with id: " + id);
        }
        alertRepository.deleteById(id);
    }

    public void deleteAlertsByTowerId(Long towerId) {
        alertRepository.deleteByTowerId(towerId);
    }
}
