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

    @Min(value = 0, message = "Humidity must be between 0 and 100")
    @Max(value = 100, message = "Humidity must be between 0 and 100")
    private Integer humidity;

    @DecimalMin(value = "0.0", message = "Wind speed cannot be negative")
    @DecimalMax(value = "500.0", message = "Wind speed cannot exceed 500 km/h")
    private Double windSpeed;

    @Min(value = 0, message = "Air quality index must be non-negative")
    @Max(value = 500, message = "Air quality index cannot exceed 500")
    private Integer airQuality;

    @Min(value = 0, message = "UV index must be non-negative")
    @Max(value = 20, message = "UV index cannot exceed 20")
    private Integer uvIndex;

    @Min(value = 800, message = "Pressure must be between 800 and 1200 hPa")
    @Max(value = 1200, message = "Pressure must be between 800 and 1200 hPa")
    private Integer pressure;

    // Network Performance
    @Min(value = 0, message = "Network load must be between 0 and 100")
    @Max(value = 100, message = "Network load must be between 0 and 100")
    private Integer networkLoad;

    @Min(value = -120, message = "Signal strength must be between -120 and 0 dBm")
    @Max(value = 0, message = "Signal strength must be between -120 and 0 dBm")
    private Integer signalStrength;

    @Min(value = 0, message = "Latency must be non-negative")
    @Max(value = 10000, message = "Latency cannot exceed 10 seconds")
    private Integer latency;

    @DecimalMin(value = "0.0", message = "Packet loss cannot be negative")
    @DecimalMax(value = "100.0", message = "Packet loss cannot exceed 100%")
    private Double packetLoss;

    @Min(value = 0, message = "Jitter must be non-negative")
    @Max(value = 1000, message = "Jitter cannot exceed 1 second")
    private Integer jitter;

    @Min(value = 0, message = "Bandwidth must be non-negative")
    @Max(value = 100000, message = "Bandwidth cannot exceed 100 Gbps")
    private Integer bandwidth;

    // Legacy fields
    @DecimalMin(value = "0.0", message = "Voltage must be non-negative")
    @DecimalMax(value = "1000.0", message = "Voltage cannot exceed 1000V")
    private Double voltage;
}
