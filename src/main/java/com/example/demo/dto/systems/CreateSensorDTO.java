package com.example.demo.dto.systems;

import jakarta.validation.constraints.NotNull;

public class CreateSensorDTO {
    @NotNull
    private String sensorType;
    @NotNull
    private String location;
    @NotNull
    private Double currentValue;
    @NotNull
    private String status;

    // Getters and setters
    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 