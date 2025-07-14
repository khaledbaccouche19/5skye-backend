package com.example.demo.dto.systems;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateBatteryDTO {
    @NotNull
    private String batteryType;
    @NotNull
    private Integer capacity;
    @NotNull
    private Double voltage;
    @NotNull
    private Double currentCharge;
    private String status;
    private String serialNumber;
    private LocalDate installationDate;

    public String getBatteryType() { return batteryType; }
    public void setBatteryType(String batteryType) { this.batteryType = batteryType; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Double getVoltage() { return voltage; }
    public void setVoltage(Double voltage) { this.voltage = voltage; }
    public Double getCurrentCharge() { return currentCharge; }
    public void setCurrentCharge(Double currentCharge) { this.currentCharge = currentCharge; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public LocalDate getInstallationDate() { return installationDate; }
    public void setInstallationDate(LocalDate installationDate) { this.installationDate = installationDate; }
} 