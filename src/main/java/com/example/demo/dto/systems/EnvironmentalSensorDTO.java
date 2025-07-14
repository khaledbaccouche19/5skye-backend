package com.example.demo.dto.systems;

public class EnvironmentalSensorDTO {
    private Long id;
    private String sensorType;
    private String location;
    private Double currentValue;
    private String status;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 