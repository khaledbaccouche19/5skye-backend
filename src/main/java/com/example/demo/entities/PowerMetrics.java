package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "power_metrics")
public class PowerMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @Column(nullable = false)
    private Double currentConsumption; // Watts

    @Column(nullable = false)
    private Double voltage;

    @Column(nullable = false)
    private Double current;

    @Column(nullable = false)
    private Double batteryLevel; // %

    @Column(nullable = false)
    private Double temperature; // Celsius

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private Double powerFactor;
    private Double frequency;
    private Double efficiency;
    private Double totalHarmonicDistortion;
    private Double peakDemand;
    private Double energyConsumed; // kWh
    private Double carbonFootprint;
    private String loadProfile;
    private String powerQuality;
    private String alarmStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private PowerSystem powerSystem;



}