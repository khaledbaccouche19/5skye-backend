package com.example.demo.dto.tower;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTowerDTO {
    @NotBlank
    private String towerName;

    @NotBlank
    private String location;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private String municipality;
    private String address;
    private String serialNumber;
    private String manufacturer;
    private String model;
    private Integer totalHeight;
    private Integer totalWeight;
    private Boolean isFullyLoaded;
    private String description;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private LocalDateTime installationDate;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    private String warrantyExpiryDate;
    private String installationCompany;
    private String operatorCompany;
} 