package com.example.demo.dto.maintenance;

import com.example.demo.entities.MaintenancePriority;
import com.example.demo.entities.MaintenanceStatus;
import com.example.demo.entities.MaintenanceType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceDTO {
    private Long id;
    private String title;
    private String description;
    private MaintenanceType type;
    private MaintenancePriority priority;
    private MaintenanceStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate scheduledDate;
    private String technician;
    private String technicianContact;
    private Integer estimatedDurationHours;
    private Integer actualDurationHours;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private String notes;
    private String partsUsed;
    private Boolean isRecurring;
    private Integer recurrenceIntervalDays;
    private LocalDate nextMaintenanceDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Long towerId;
    private String towerName;
}
