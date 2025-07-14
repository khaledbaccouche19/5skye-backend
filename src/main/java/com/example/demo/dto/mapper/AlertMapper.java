package com.example.demo.dto.mapper;

import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.alert.CreateAlertDTO;
import com.example.demo.entities.Alert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertMapper {

    public AlertDTO toDto(Alert entity) {
        if (entity == null) return null;
        AlertDTO dto = new AlertDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setSeverity(entity.getSeverity());
        dto.setSource(entity.getSource());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setTower(entity.getTower() != null ? entity.getTower().getTowerName() : null);
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }

    public Alert toEntity(AlertDTO dto) {
        if (dto == null) return null;
        Alert entity = new Alert();
        entity.setId(dto.getId());
        entity.setMessage(dto.getMessage());
        entity.setSeverity(dto.getSeverity());
        entity.setSource(dto.getSource());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setTimestamp(dto.getTimestamp());
        // Tower mapping skipped (requires lookup by name if needed)
        return entity;
    }

    public Alert toEntity(CreateAlertDTO dto) {
        if (dto == null) return null;
        Alert entity = new Alert();
        entity.setMessage(dto.getMessage());
        entity.setSeverity(dto.getSeverity());
        entity.setSource(dto.getSource());
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setTimestamp(dto.getTimestamp());
        // Tower mapping skipped (requires lookup by name if needed)
        return entity;
    }

    public List<AlertDTO> toDtoList(List<Alert> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
