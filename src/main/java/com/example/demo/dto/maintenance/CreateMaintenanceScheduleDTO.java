package com.example.demo.dto.maintenance;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateMaintenanceScheduleDTO {
    @NotNull
    private String description;
    @NotNull
    private LocalDate scheduledDate;
    @NotNull
    private String status;

    // Getters and setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 