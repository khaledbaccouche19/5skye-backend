package com.example.demo.dto.systems;

import jakarta.validation.constraints.NotNull;

public class CreatePowerSystemDTO {
    @NotNull
    private String systemType;
    @NotNull
    private Double voltage;
    @NotNull
    private Double current;
    @NotNull
    private String status;

    // Getters and setters
    public String getSystemType() { return systemType; }
    public void setSystemType(String systemType) { this.systemType = systemType; }
    public Double getVoltage() { return voltage; }
    public void setVoltage(Double voltage) { this.voltage = voltage; }
    public Double getCurrent() { return current; }
    public void setCurrent(Double current) { this.current = current; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 