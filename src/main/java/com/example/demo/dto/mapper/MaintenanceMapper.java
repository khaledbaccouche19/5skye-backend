package com.example.demo.dto.mapper;

import com.example.demo.dto.maintenance.CreateMaintenanceDTO;
import com.example.demo.dto.maintenance.MaintenanceDTO;
import com.example.demo.entities.Maintenance;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceMapper {

    public MaintenanceDTO toDTO(Maintenance maintenance) {
        if (maintenance == null) {
            return null;
        }

        return MaintenanceDTO.builder()
                .id(maintenance.getId())
                .title(maintenance.getTitle())
                .description(maintenance.getDescription())
                .type(maintenance.getType())
                .priority(maintenance.getPriority())
                .status(maintenance.getStatus())
                .startDate(maintenance.getStartDate())
                .endDate(maintenance.getEndDate())
                .scheduledDate(maintenance.getScheduledDate())
                .technician(maintenance.getTechnician())
                .technicianContact(maintenance.getTechnicianContact())
                .estimatedDurationHours(maintenance.getEstimatedDurationHours())
                .actualDurationHours(maintenance.getActualDurationHours())
                .estimatedCost(maintenance.getEstimatedCost())
                .actualCost(maintenance.getActualCost())
                .notes(maintenance.getNotes())
                .partsUsed(maintenance.getPartsUsed())
                .isRecurring(maintenance.getIsRecurring())
                .recurrenceIntervalDays(maintenance.getRecurrenceIntervalDays())
                .nextMaintenanceDate(maintenance.getNextMaintenanceDate())
                .createdAt(maintenance.getCreatedAt())
                .updatedAt(maintenance.getUpdatedAt())
                .towerId(maintenance.getTower() != null ? maintenance.getTower().getId() : null)
                .towerName(maintenance.getTower() != null ? maintenance.getTower().getName() : null)
                .build();
    }

    public Maintenance toEntity(CreateMaintenanceDTO dto) {
        if (dto == null) {
            return null;
        }

        return Maintenance.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .type(dto.getType())
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .scheduledDate(dto.getScheduledDate())
                .technician(dto.getTechnician())
                .technicianContact(dto.getTechnicianContact())
                .estimatedDurationHours(dto.getEstimatedDurationHours())
                .actualDurationHours(dto.getActualDurationHours())
                .estimatedCost(dto.getEstimatedCost())
                .actualCost(dto.getActualCost())
                .notes(dto.getNotes())
                .partsUsed(dto.getPartsUsed())
                .isRecurring(dto.getIsRecurring())
                .recurrenceIntervalDays(dto.getRecurrenceIntervalDays())
                .nextMaintenanceDate(dto.getNextMaintenanceDate())
                .build();
    }
}
