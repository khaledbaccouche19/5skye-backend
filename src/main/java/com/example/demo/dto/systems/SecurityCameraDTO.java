package com.example.demo.dto.systems;

public class SecurityCameraDTO {
    private Long id;
    private String cameraName;
    private String ipAddress;
    private String resolution;
    private String status;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCameraName() { return cameraName; }
    public void setCameraName(String cameraName) { this.cameraName = cameraName; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 