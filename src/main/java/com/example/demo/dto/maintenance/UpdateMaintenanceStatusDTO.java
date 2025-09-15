package com.example.demo.dto.maintenance;

import com.example.demo.entities.MaintenanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaintenanceStatusDTO {
    
    private MaintenanceStatus status;
    private LocalDate endDate;
    private Integer actualDurationHours;
    private BigDecimal actualCost;
    private String completionNotes;
    private String technicianNotes;
    private String qualityCheckNotes;
    private String nextMaintenanceRecommendation;
}
