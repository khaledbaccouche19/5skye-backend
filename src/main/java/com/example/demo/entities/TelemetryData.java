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

    @Column(name = "timestamp", nullable = false)
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

    @Column(name = "humidity")
    private Integer humidity; // 0-100%

    @Column(name = "wind_speed")
    private Double windSpeed; // km/h

    @Column(name = "air_quality")
    private Integer airQuality; // Air quality index

    @Column(name = "uv_index")
    private Integer uvIndex; // UV index

    @Column(name = "pressure")
    private Integer pressure; // hPa

    // Network Performance
    @Column(name = "network_load")
    private Integer networkLoad; // 0-100%

    @Column(name = "signal_strength")
    private Integer signalStrength; // dBm (negative values)

    @Column(name = "latency")
    private Integer latency; // milliseconds

    @Column(name = "packet_loss")
    private Double packetLoss; // percentage (0.0-100.0)

    @Column(name = "jitter")
    private Integer jitter; // milliseconds

    @Column(name = "bandwidth")
    private Integer bandwidth; // Mbps

    // Legacy fields (keeping for backward compatibility)
    @Column(name = "voltage")
    private Double voltage;

    @Column(name = "tower_id", nullable = false)
    private Long towerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tower_id", insertable = false, updatable = false)
    private Tower tower;
}
