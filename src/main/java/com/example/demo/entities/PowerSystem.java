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
@Table(name = "power_systems")
public class PowerSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long powerSystemId;

    @Enumerated(EnumType.STRING)
    private PowerSystemType systemType; // AC_INPUT, DC_OUTPUT, RECTIFIER, INVERTER

    @Column(nullable = false)
    private Double voltage;

    @Column(nullable = false)
    private Double current;

    @Column(nullable = false)
    private Double maxOutput;

    @Enumerated(EnumType.STRING)
    private PowerSystemStatus status; // ONLINE, OFFLINE, FAULT, MAINTENANCE

    private String manufacturer;
    private String model;
    private String serialNumber;
    private Integer phases; // 1 or 3 phase
    private Double frequency; // 50/60 Hz
    private Double efficiency;
    private String inputVoltageRange;
    private String outputVoltageRange;
    private Double powerFactor;
    private Double totalHarmonicDistortion;
    private Integer rackUnits;
    private Double weight;
    private String dimensions;
    private String operatingTemperature;
    private String certifications;
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

    @OneToMany(mappedBy = "powerSystem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PowerMetrics> metrics = new ArrayList<>();



}