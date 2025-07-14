package com.example.demo.dto.systems;

import jakarta.validation.constraints.NotNull;

public class CreateNetworkEquipmentDTO {
    @NotNull
    private String equipmentName;
    @NotNull
    private String manufacturer;
    @NotNull
    private String model;
    @NotNull
    private String status;

    // Getters and setters
    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 