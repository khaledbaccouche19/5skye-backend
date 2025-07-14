package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String metricType;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private String status;

    private String description;
    private String category;
    private String source;
    private String method;
    private String accuracy;
    private String precision;
    private String uncertainty;
    private String calibrationStatus;
    private String validationStatus;
    private String dataQuality;
    private String processingAlgorithm;
    private String aggregationMethod;
    private String samplingRate;
    private String filterApplied;
    private String noiseLevel;
    private String signalStrength;
    private String compensationApplied;
    private String referenceStandard;
    private String traceability;
    private String confidenceLevel;
    private String statisticalAnalysis;
    private String trendAnalysis;
    private String anomalyDetection;
    private String correlationAnalysis;
    private String predictiveAnalysis;
    private String thresholdComparison;
    private String alarmStatus;
    private String alertLevel;
    private String actionRequired;
    private String actionTaken;
    private String responseTime;
    private String escalationLevel;
    private String assignedTo;
    private String resolutionStatus;
    private String resolutionTime;
    private String followUpRequired;
    private String reportGenerated;
    private String documentationUpdated;
    private String auditTrail;
    private String complianceCheck;
    private String regulatoryRequirement;
    private String performanceIndicator;
    private String benchmarkComparison;
    private String improvementOpportunity;
    private String recommendedAction;
    private String costImplication;
    private String businessImpact;
    private String riskAssessment;
    private String mitigationStrategy;
    private String contingencyPlan;
    private String lessonLearned;
    private String bestPractice;
    private String knowledgeBase;
    private String decisionSupport;
    private String automationLevel;
    private String humanIntervention;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;
}
