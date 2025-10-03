package com.example.demo.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictiveInsightDTO {
    private Long towerId;
    private String title;              // e.g., "Battery will reach 15% in ~3 days"
    private String recommendation;     // action suggestion
    private String type;               // e.g., "Predictive Maintenance"
    private String riskType;           // e.g., "Hardware Failure", "Thermal", "Performance"
    private String urgency;            // INFO | WARNING | HIGH | CRITICAL
    private int confidence;            // 0-100
    private double estimatedImpact;    // arbitrary units or cost score
    private Instant predictedBy;       // predicted deadline

    // Evidence
    private String metric;             // primary driver metric
    private Double currentValue;
    private Double trendPerHour;       // slope per hour if applicable
}


