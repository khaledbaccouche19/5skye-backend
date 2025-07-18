package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentId;

    @Enumerated(EnumType.STRING)
    private EquipmentCategory category; // COOLING, LIGHTING, SECURITY, POWER, NETWORK, MONITORING

    @Column(nullable = false)
    private String equipmentName;

    @Column(nullable = false)
    private String equipmentType;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status; // ACTIVE, INACTIVE, FAULT, MAINTENANCE

    private String serialNumber;
    private String description;
    private String specifications;
    private String partNumber;
    private String assetTag;
    private String location;
    private String installationPosition;
    private String powerRequirement;
    private String operatingVoltage;
    private String operatingCurrent;
    private String powerConsumption;
    private String operatingTemperature;
    private String storageTemperature;
    private String operatingHumidity;
    private String dimensions;
    private String weight;
    private String material;
    private String color;
    private String finishType;
    private String protectionRating;
    private String certifications;
    private String complianceStandards;
    private String warrantyPeriod;
    private String warrantyProvider;
    private String serviceProvider;
    private String supportContact;
    private String documentationPath;
    private String manualPath;
    private String drawingPath;
    private String certificatePath;
    private String purchaseOrder;
    private String vendor;
    private String purchaseDate;
    private String purchasePrice;
    private String deliveryDate;
    private String acceptanceDate;
    private String commissioningDate;
    private LocalDateTime installationDate;
    private String installationBy;
    private String installationNotes;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    private String maintenanceSchedule;
    private String maintenanceProvider;
    private String maintenanceNotes;
    private String spareParts;
    private String sparePartsLocation;
    private String troubleshootingGuide;
    private String replacementProcedure;
    private String disposalProcedure;
    private String environmentalImpact;
    private String recyclingInformation;
    private String riskAssessment;
    private String safetyPrecautions;
    private String operatingProcedure;
    private String emergencyProcedure;
    private String calibrationRequirement;
    private String calibrationSchedule;
    private String calibrationProvider;
    private LocalDateTime lastCalibrationDate;
    private LocalDateTime nextCalibrationDate;
    private String calibrationCertificate;
    private String performanceMonitoring;
    private String alarmConfiguration;
    private String eventLogging;
    private String dataRetention;
    private String backupConfiguration;
    private String securityConfiguration;
    private String accessControl;
    private String networkConfiguration;
    private String ipAddress;
    private String macAddress;
    private String firmwareVersion;
    private String softwareVersion;
    private String configurationVersion;
    private String licenseInformation;
    private String updateSchedule;
    private String patchManagement;
    private String vulnerabilityAssessment;
    private String securityAudit;
    private String complianceAudit;
    private String performanceAudit;
    private String qualityAssurance;
    private String changeManagement;
    private String configurationManagement;
    private String assetManagement;
    private String lifecycleManagement;
    private String replacementPlan;
    private String upgradeSchedule;
    private String migrationPlan;
    private String retirementPlan;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private TowerUnit unit;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EquipmentMetrics> equipmentMetrics = new ArrayList<>();

    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private PowerSystem powerSystem;

    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private SecurityCamera securityCamera;

    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private NetworkEquipment networkEquipment;
}