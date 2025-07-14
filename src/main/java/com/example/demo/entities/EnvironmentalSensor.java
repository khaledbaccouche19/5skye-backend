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
@Table(name = "environmental_sensors")
public class EnvironmentalSensor {
    @Id
    private Long sensorId;




    @Enumerated(EnumType.STRING)
    private SensorType sensorType; // TEMPERATURE, HUMIDITY, SMOKE, WATER, DOOR, MOTION, VIBRATION

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private SensorStatus status; // ACTIVE, INACTIVE, FAULT, CALIBRATION

    @Column(nullable = false)
    private Double currentValue;

    @Column(nullable = false)
    private Double minThreshold;

    @Column(nullable = false)
    private Double maxThreshold;

    private String manufacturer;
    private String model;
    private String serialNumber;
    private String sensorTechnology;
    private String measurementUnit;
    private String measurementRange;
    private String accuracy;
    private String resolution;
    private String responseTime;
    private String operatingTemperature;
    private String operatingHumidity;
    private String powerRequirement;
    private String outputSignal;
    private String communicationProtocol;
    private String calibrationInterval;
    private LocalDateTime lastCalibrationDate;
    private LocalDateTime nextCalibrationDate;
    private String calibrationCertificate;
    private String installationMethod;
    private String cableLength;
    private String enclosureType;
    private String protectionRating;
    private String certifications;
    private String warrantyPeriod;
    private String maintenanceRequirements;
    private String troubleshootingGuide;
    private String sparePartsAvailable;
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

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SensorReading> readings = new ArrayList<>();

}