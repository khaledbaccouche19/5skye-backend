package com.example.demo.dto.systems;

import jakarta.validation.constraints.NotNull;

public class CreateSecurityCameraDTO {
    @NotNull
    private String cameraName;
    @NotNull
    private String ipAddress;
    @NotNull
    private String resolution;
    @NotNull
    private String status;

    // Getters and setters
    public String getCameraName() { return cameraName; }
    public void setCameraName(String cameraName) { this.cameraName = cameraName; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 