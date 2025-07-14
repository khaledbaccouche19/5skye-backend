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
@Table(name = "display_metrics")
public class DisplayMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String status;

    private Double brightness;
    private Double temperature;
    private Double powerConsumption;
    private Double voltage;
    private Double current;
    private String pixelHealth;
    private String moduleStatus;
    private String colorAccuracy;
    private String refreshRate;
    private String errorCount;
    private String uptime;
    private String networkLatency;
    private String signalStrength;
    private String contentPlayback;
    private String schedulerStatus;
    private String fanSpeed;
    private String humidityLevel;
    private String ambientLight;
    private String viewingConditions;
    private String maintenanceAlerts;

    @ManyToOne(fetch = FetchType.LAZY)
    private LEDDisplay display;

}