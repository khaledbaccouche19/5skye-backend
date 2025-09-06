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
    private Double ambientTemperature;
    private Double humidity;
    private Double windSpeed;
    private Integer windDirection;
    private Integer airQuality;
    private Double uvIndex;
    private Double pressure;
    private Double precipitation;

    // Network Performance
    private Double networkLoad;
    private Double signalStrength;
    private Double latency;
    private Double packetLoss;
    private Double jitter;
    private Double bandwidth;
    private Double throughput;
    private Double responseTime;
    private Double interference;

    // System Performance
    private Double cpuUtilization;
    private Double memoryUsage;
    private Double diskSpace;
    private Double errorRate;

    // Physical Sensors
    private Double vibration;

    // Legacy fields
    private Double voltage;
}
