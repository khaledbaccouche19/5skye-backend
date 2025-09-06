package com.example.demo.dto.telemetry;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.Instant;

@Data
public class CreateTelemetryDataDTO {

    @NotNull(message = "Tower ID is required")
    private Long towerId;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    // Core Tower Status
    @Pattern(regexp = "^(online|offline|warning|critical)$", 
             message = "Status must be one of: online, offline, warning, critical")
    private String status;

    @Min(value = 0, message = "Battery must be between 0 and 100")
    @Max(value = 100, message = "Battery must be between 0 and 100")
    private Integer battery;

    @DecimalMin(value = "0.0", message = "Uptime must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", message = "Uptime must be between 0.0 and 100.0")
    private Double uptime;

    // Environmental Conditions
    @DecimalMin(value = "-273.15", message = "Temperature cannot be below absolute zero")
    @DecimalMax(value = "100.0", message = "Temperature cannot exceed 100°C")
    private Double temperature;

    @DecimalMin(value = "0.0", message = "Ambient temperature cannot be below absolute zero")
    @DecimalMax(value = "100.0", message = "Ambient temperature cannot exceed 100°C")
    private Double ambientTemperature;

    @DecimalMin(value = "0.0", message = "Humidity must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", message = "Humidity must be between 0.0 and 100.0")
    private Double humidity;

    @DecimalMin(value = "0.0", message = "Wind speed cannot be negative")
    @DecimalMax(value = "500.0", message = "Wind speed cannot exceed 500 km/h")
    private Double windSpeed;

    @Min(value = 0, message = "Wind direction must be between 0 and 360 degrees")
    @Max(value = 360, message = "Wind direction must be between 0 and 360 degrees")
    private Integer windDirection;

    @Min(value = 0, message = "Air quality index must be non-negative")
    @Max(value = 500, message = "Air quality index cannot exceed 500")
    private Integer airQuality;

    @DecimalMin(value = "0.0", message = "UV index must be non-negative")
    @DecimalMax(value = "20.0", message = "UV index cannot exceed 20")
    private Double uvIndex;

    @DecimalMin(value = "800.0", message = "Pressure must be between 800 and 1200 hPa")
    @DecimalMax(value = "1200.0", message = "Pressure must be between 800 and 1200 hPa")
    private Double pressure;

    @DecimalMin(value = "0.0", message = "Precipitation cannot be negative")
    @DecimalMax(value = "1000.0", message = "Precipitation cannot exceed 1000 mm/h")
    private Double precipitation;

    // Network Performance
    @DecimalMin(value = "0.0", message = "Network load must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", message = "Network load must be between 0.0 and 100.0")
    private Double networkLoad;

    @DecimalMin(value = "-120.0", message = "Signal strength must be between -120.0 and 0.0 dBm")
    @DecimalMax(value = "0.0", message = "Signal strength must be between -120.0 and 0.0 dBm")
    private Double signalStrength;

    @DecimalMin(value = "0.0", message = "Latency must be non-negative")
    @DecimalMax(value = "10000.0", message = "Latency cannot exceed 10 seconds")
    private Double latency;

    @DecimalMin(value = "0.0", message = "Packet loss cannot be negative")
    @DecimalMax(value = "100.0", message = "Packet loss cannot exceed 100%")
    private Double packetLoss;

    @DecimalMin(value = "0.0", message = "Jitter must be non-negative")
    @DecimalMax(value = "1000.0", message = "Jitter cannot exceed 1 second")
    private Double jitter;

    @DecimalMin(value = "0.0", message = "Bandwidth must be non-negative")
    @DecimalMax(value = "100000.0", message = "Bandwidth cannot exceed 100 Gbps")
    private Double bandwidth;

    @DecimalMin(value = "0.0", message = "Throughput must be non-negative")
    @DecimalMax(value = "100000.0", message = "Throughput cannot exceed 100 Gbps")
    private Double throughput;

    @DecimalMin(value = "0.0", message = "Response time must be non-negative")
    @DecimalMax(value = "10000.0", message = "Response time cannot exceed 10 seconds")
    private Double responseTime;

    @DecimalMin(value = "0.0", message = "Interference must be non-negative")
    @DecimalMax(value = "100.0", message = "Interference cannot exceed 100 dB")
    private Double interference;

    // System Performance
    @DecimalMin(value = "0.0", message = "CPU utilization must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", message = "CPU utilization must be between 0.0 and 100.0")
    private Double cpuUtilization;

    @DecimalMin(value = "0.0", message = "Memory usage must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", message = "Memory usage must be between 0.0 and 100.0")
    private Double memoryUsage;

    @DecimalMin(value = "0.0", message = "Disk space must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", message = "Disk space must be between 0.0 and 100.0")
    private Double diskSpace;

    @DecimalMin(value = "0.0", message = "Error rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Error rate cannot exceed 100%")
    private Double errorRate;

    // Physical Sensors
    @DecimalMin(value = "0.0", message = "Vibration must be non-negative")
    @DecimalMax(value = "100.0", message = "Vibration cannot exceed 100 g-force")
    private Double vibration;

    // Legacy fields
    @DecimalMin(value = "0.0", message = "Voltage must be non-negative")
    @DecimalMax(value = "1000.0", message = "Voltage cannot exceed 1000V")
    private Double voltage;
}
