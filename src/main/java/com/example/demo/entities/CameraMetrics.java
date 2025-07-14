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
@Table(name = "camera_metrics")
public class CameraMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String status;

    private Double cpuUsage;
    private Double memoryUsage;
    private Double temperature;
    private Double bandwidth;
    private Integer frameRate;
    private String imageQuality;
    private String networkLatency;
    private String storageUsage;
    private String compressionRatio;
    private String bitrate;
    private String streamHealth;
    private String errorCount;
    private String uptime;
    private String focusStatus;
    private String exposureSettings;
    private String whiteBalanceSettings;
    private String noiseLevel;
    private String motionActivity;
    private String alertsTriggered;

    @ManyToOne(fetch = FetchType.LAZY)
    private SecurityCamera camera;


}