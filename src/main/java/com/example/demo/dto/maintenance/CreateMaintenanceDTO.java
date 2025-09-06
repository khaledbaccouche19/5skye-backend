package com.example.demo.dto.maintenance;

import com.example.demo.entities.MaintenancePriority;
import com.example.demo.entities.MaintenanceStatus;
import com.example.demo.entities.MaintenanceType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMaintenanceDTO {
    
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Maintenance type is required")
    private MaintenanceType type;

    @NotNull(message = "Priority is required")
    private MaintenancePriority priority;

    @NotNull(message = "Status is required")
    private MaintenanceStatus status;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate scheduledDate;

    @Size(max = 100, message = "Technician name cannot exceed 100 characters")
    private String technician;

    @Size(max = 100, message = "Technician contact cannot exceed 100 characters")
    private String technicianContact;

    @Min(value = 0, message = "Estimated duration must be positive")
    private Integer estimatedDurationHours;

    @Min(value = 0, message = "Actual duration must be positive")
    private Integer actualDurationHours;

    @DecimalMin(value = "0.0", message = "Estimated cost must be positive")
    private BigDecimal estimatedCost;

    @DecimalMin(value = "0.0", message = "Actual cost must be positive")
    private BigDecimal actualCost;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    @Size(max = 500, message = "Parts used cannot exceed 500 characters")
    private String partsUsed;

    private Boolean isRecurring = false;

    @Min(value = 1, message = "Recurrence interval must be at least 1 day")
    private Integer recurrenceIntervalDays;

    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Tower ID is required")
    private Long towerId;
}
