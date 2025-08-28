package com.example.demo.dto.mapper;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.alert.AlertSummaryDTO;
import com.example.demo.dto.alert.CreateAlertDTO;
import com.example.demo.entities.Alert;
import com.example.demo.entities.AlertSeverity;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public AlertDTO toDTO(Alert alert) {
        if (alert == null) {
            return null;
        }

        return AlertDTO.builder()
                .id(alert.getId())
                .timestamp(alert.getTimestamp())
                .message(alert.getMessage())
                .severity(alert.getSeverity() != null ? alert.getSeverity().name().toLowerCase() : null)
                .towerId(alert.getTowerId())
                .towerName(alert.getTowerName())
                .resolved(alert.getResolved())
                .build();
    }

    public Alert toEntity(CreateAlertDTO createAlertDTO) {
        if (createAlertDTO == null) {
            return null;
        }

        return Alert.builder()
                .message(createAlertDTO.getMessage())
                .severity(parseAlertSeverity(createAlertDTO.getSeverity()))
                .towerId(createAlertDTO.getTowerId())
                .towerName(createAlertDTO.getTowerName())
                .resolved(createAlertDTO.getResolved() != null ? createAlertDTO.getResolved() : false)
                .build();
    }

    public AlertSummaryDTO toSummaryDTO(Alert alert) {
        if (alert == null) {
            return null;
        }

        return AlertSummaryDTO.builder()
                .id(alert.getId())
                .message(alert.getMessage())
                .severity(alert.getSeverity() != null ? alert.getSeverity().name().toLowerCase() : null)
                .build();
    }

    private AlertSeverity parseAlertSeverity(String severity) {
        if (severity == null) {
            return AlertSeverity.INFO;
        }
        try {
            return AlertSeverity.valueOf(severity.toUpperCase());
        } catch (IllegalArgumentException e) {
            return AlertSeverity.INFO;
        }
    }
}
