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

//equipmentId
@Entity
@Getter
@Setter
@AllArgsConstructor

@NoArgsConstructor
@Table(name = "network_equipment")
public class NetworkEquipment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long equipmentId;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType; // ANTENNA, RADIO_UNIT, BASEBAND, ROUTER, SWITCH

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status; // ONLINE, OFFLINE, FAULT, MAINTENANCE

    @Column(nullable = false)
    private Double signalStrength;

    private String serialNumber;
    private String macAddress;
    private String firmwareVersion;
    private String softwareVersion;
    private String licenseKey;
    private String frequency;
    private String bandwidth;
    private String powerOutput;
    private String sensitivity;
    private String gain;
    private String polarization;
    private String beamwidth;
    private String vswr;
    private String impedance;
    private String connectorType;
    private String cableType;
    private String cableLength;
    private String antennaHeight;
    private String antennaOrientation;
    private String coverageArea;
    private String operatingTemperature;
    private String weatherResistance;
    private String lightningProtection;
    private String groundingSystem;
    private String rackUnits;
    private String powerRequirement;
    private String coolingMethod;
    private String portConfiguration;
    private String vlanSupport;
    private String qosSupport;
    private String securityFeatures;
    private String managementProtocol;
    private String monitoringCapability;
    private String redundancyFeatures;
    private String failoverCapability;
    private String loadBalancing;
    private String trafficShaping;
    private String networkTopology;
    private String routingProtocols;
    private String switchingCapacity;
    private String packetProcessingRate;
    private String latency;
    private String jitter;
    private String packetLoss;
    private String throughput;
    private String errorRate;
    private String uptime;
    private String certifications;
    private String complianceStandards;
    private LocalDateTime installationDate;
    private LocalDateTime lastMaintenanceDate;
    private String maintenanceNotes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NetworkMetrics> networkMetrics = new ArrayList<>();




}