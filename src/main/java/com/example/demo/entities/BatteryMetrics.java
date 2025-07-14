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

@Table(name = "battery_metrics")
public class BatteryMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @Column(nullable = false)
    private Double chargeLevel; // %

    @Column(nullable = false)
    private Double voltage;

    @Column(nullable = false)
    private Double current;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private Double stateOfHealth; // %
    private Double internalResistance;
    private Integer cycleCount;
    private String chargingState; // CHARGING, DISCHARGING, IDLE
    private Double timeToEmpty; // hours
    private Double timeToFull; // hours
    private Double cellVoltage1;
    private Double cellVoltage2;
    private Double cellVoltage3;
    private Double cellVoltage4;
    private Boolean isBalancing;
    private String alarmStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Battery battery;


}