package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "telemetry_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telemetry_id")
    private Long id;

    @Column(name = "time", nullable = false)
    private Instant timestamp;

    // Core Tower Status
    @Column(name = "status")
    private String status; // online, offline, warning, critical

    @Column(name = "battery")
    private Integer battery; // 0-100%

    @Column(name = "uptime")
    private Double uptime; // 0.0-100.0%

    // Environmental Conditions
    @Column(name = "temperature")
    private Double temperature; // Celsius

    @Column(name = "ambient_temperature")
    private Double ambientTemperature; // Ambient temperature in Celsius

    @Column(name = "humidity")
    private Double humidity; // 0-100% (changed to Double for precision)

    @Column(name = "wind_speed")
    private Double windSpeed; // km/h

    @Column(name = "wind_direction")
    private Integer windDirection; // degrees 0-360

    @Column(name = "air_quality")
    private Integer airQuality; // Air quality index

    @Column(name = "uv_index")
    private Double uvIndex; // UV index (changed to Double for precision)

    @Column(name = "pressure")
    private Double pressure; // hPa (changed to Double for precision)

    @Column(name = "precipitation")
    private Double precipitation; // mm/h

    // Network Performance
    @Column(name = "network_load")
    private Double networkLoad; // 0-100% (changed to Double for precision)

    @Column(name = "signal_strength")
    private Double signalStrength; // dBm (negative values, changed to Double)

    @Column(name = "latency")
    private Double latency; // milliseconds (changed to Double for precision)

    @Column(name = "packet_loss")
    private Double packetLoss; // percentage (0.0-100.0)

    @Column(name = "jitter")
    private Double jitter; // milliseconds (changed to Double for precision)

    @Column(name = "bandwidth")
    private Double bandwidth; // Mbps (changed to Double for precision)

    @Column(name = "throughput")
    private Double throughput; // Mbps

    @Column(name = "response_time")
    private Double responseTime; // milliseconds

    @Column(name = "interference")
    private Double interference; // dB

    // System Performance
    @Column(name = "cpu_utilization")
    private Double cpuUtilization; // 0-100%

    @Column(name = "memory_usage")
    private Double memoryUsage; // 0-100%

    @Column(name = "disk_space")
    private Double diskSpace; // 0-100%

    @Column(name = "error_rate")
    private Double errorRate; // percentage

    // Physical Sensors
    @Column(name = "vibration")
    private Double vibration; // g-force

    // Legacy fields (keeping for backward compatibility)
    @Column(name = "voltage")
    private Double voltage;

    @Column(name = "tower_id", nullable = false)
    private Long towerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tower_id", insertable = false, updatable = false)
    private Tower tower;
}
