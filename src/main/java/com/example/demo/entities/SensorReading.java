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
@Table(name = "sensor_readings")
public class SensorReading {
    @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long readingId;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private Boolean isAlarm;

    private String readingType; // NORMAL, WARNING, CRITICAL
    private String dataQuality;
    private String calibrationStatus;
    private String measurementMethod;
    private String environmentalConditions;
    private String operatorNotes;
    private String validationStatus;
    private String dataSource;
    private String processingAlgorithm;
    private String aggregationMethod;
    private String samplingRate;
    private String filterApplied;
    private String noiseLevel;
    private String signalStrength;
    private String compensationApplied;
    private String referenceStandard;
    private String traceability;
    private String uncertainty;
    private String confidenceLevel;
    private String statisticalAnalysis;
    private String trendAnalysis;
    private String anomalyDetection;
    private String correlationAnalysis;
    private String predictiveAnalysis;
    private String reportGenerated;
    private String actionTaken;
    private String followUpRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    private EnvironmentalSensor sensor;


}