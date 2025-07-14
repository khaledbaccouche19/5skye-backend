package com.example.demo.dto.systems;

import jakarta.validation.constraints.NotNull;

public class CreateLEDDisplayDTO {
    @NotNull
    private String displayName;
    @NotNull
    private Double pixelPitch;
    @NotNull
    private Integer brightness;
    @NotNull
    private String resolution;
    @NotNull
    private String status;

    // Getters and setters
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public Double getPixelPitch() { return pixelPitch; }
    public void setPixelPitch(Double pixelPitch) { this.pixelPitch = pixelPitch; }
    public Integer getBrightness() { return brightness; }
    public void setBrightness(Integer brightness) { this.brightness = brightness; }
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 