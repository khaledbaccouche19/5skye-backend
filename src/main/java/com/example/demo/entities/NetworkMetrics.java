package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
//metricId
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "network_metrics")
public class NetworkMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Double signalStrength;

    @Column(nullable = false)
    private Double throughput;

    @Column(nullable = false)
    private Double latency;

    @Column(nullable = false)
    private Double packetLoss;

    private Double jitter;
    private Double errorRate;
    private Double bandwidth;
    private Double cpuUsage;
    private Double memoryUsage;
    private Double temperature;
    private String interfaceStatus;
    private String linkStatus;
    private String networkLoad;
    private String trafficVolume;
    private String activeConnections;
    private String sessionCount;
    private String queueDepth;
    private String bufferUtilization;
    private String errorCount;
    private String retransmissionRate;
    private String congestionLevel;
    private String availabilityPercentage;
    private String performanceIndex;
    private String serviceLevel;
    private String alarmStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private NetworkEquipment equipment;
}