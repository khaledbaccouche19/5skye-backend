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
public class AnomalyDTO {

    private Long towerId;
    private Instant timestamp;

    // Metric identifier (e.g., "temperature", "battery", "latency")
    private String metric;

    // Observed value
    private Double value;

    // Standard score of the observation within the analysis window
    private Double zScore;

    // Derived severity based on |zScore|
    private String severity; // INFO, WARNING, HIGH, CRITICAL

    // Context from the analysis window
    private Double mean;
    private Double stdDev;
    private Double lowerBound; // mean - k * std
    private Double upperBound; // mean + k * std
}


