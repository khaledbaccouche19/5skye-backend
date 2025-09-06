package com.example.demo.dto.mapper;

import com.example.demo.dto.tower.CreateTowerDTO;
import com.example.demo.dto.tower.TowerDTO;
import com.example.demo.dto.tower.TowerSummaryDTO;
import com.example.demo.entities.Tower;
import com.example.demo.entities.TowerStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TowerMapper {

    public TowerDTO toDTO(Tower tower) {
        if (tower == null) {
            return null;
        }

        return TowerDTO.builder()
                .id(tower.getId())
                .name(tower.getName())
                .status(tower.getStatus() != null ? tower.getStatus().name().toLowerCase() : null)
                .latitude(tower.getLatitude())
                .longitude(tower.getLongitude())
                .city(tower.getCity())
                .battery(tower.getBattery())
                .temperature(tower.getTemperature())
                .uptime(tower.getUptime())
                .networkLoad(tower.getNetworkLoad())
                .useCase(tower.getUseCase())
                .region(tower.getRegion())
                .lastMaintenance(tower.getLastMaintenance())
                .model3dPath(tower.getModel3dPath())
                .apiEndpointUrl(tower.getApiEndpointUrl())
                .apiKey(tower.getApiKey())
                .createdAt(tower.getCreatedAt())
                .updatedAt(tower.getUpdatedAt())
                .build();
    }

    public Tower toEntity(CreateTowerDTO createTowerDTO) {
        if (createTowerDTO == null) {
            return null;
        }

        return Tower.builder()
                .name(createTowerDTO.getName())
                .status(parseTowerStatus(createTowerDTO.getStatus()))
                .latitude(createTowerDTO.getLatitude())
                .longitude(createTowerDTO.getLongitude())
                .city(createTowerDTO.getCity())
                .battery(createTowerDTO.getBattery())
                .temperature(createTowerDTO.getTemperature())
                .uptime(createTowerDTO.getUptime())
                .networkLoad(createTowerDTO.getNetworkLoad())
                .useCase(createTowerDTO.getUseCase())
                .region(createTowerDTO.getRegion())
                .lastMaintenance(parseLocalDate(createTowerDTO.getLastMaintenance()))
                .model3dPath(createTowerDTO.getModel3dPath())
                .apiEndpointUrl(createTowerDTO.getApiEndpointUrl())
                .apiKey(createTowerDTO.getApiKey())
                .build();
    }

    public TowerSummaryDTO toSummaryDTO(Tower tower) {
        if (tower == null) {
            return null;
        }

        return TowerSummaryDTO.builder()
                .id(tower.getId())
                .name(tower.getName())
                .status(tower.getStatus() != null ? tower.getStatus().name().toLowerCase() : null)
                .build();
    }

    private TowerStatus parseTowerStatus(String status) {
        if (status == null) {
            return null;
        }
        try {
            return TowerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return TowerStatus.OFFLINE;
        }
    }

    private LocalDate parseLocalDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return null;
        }
    }
} 