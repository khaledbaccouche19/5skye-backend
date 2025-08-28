package com.example.demo.dto.telemetry;

import lombok.Data;
import lombok.Builder;

import java.time.Instant;

@Data
@Builder
public class TelemetryDataDTO {

    private Long id;
    private Long towerId;
    private Instant timestamp;

    // Core Tower Status
    private String status;
    private Integer battery;
    private Double uptime;

    // Environmental Conditions
    private Double temperature;
    private Integer humidity;
    private Double windSpeed;
    private Integer airQuality;
    private Integer uvIndex;
    private Integer pressure;

    // Network Performance
    private Integer networkLoad;
    private Integer signalStrength;
    private Integer latency;
    private Double packetLoss;
    private Integer jitter;
    private Integer bandwidth;

    // Legacy fields
    private Double voltage;
}
