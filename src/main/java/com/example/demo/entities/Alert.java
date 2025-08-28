package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long id;
    
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;
    
    @Column(name = "message", nullable = false)
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private AlertSeverity severity;
    
    @Column(name = "tower_id", nullable = false)
    private Long towerId;
    
    @Column(name = "tower_name")
    private String towerName;
    
    @Column(name = "resolved", nullable = false)
    private Boolean resolved;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tower_id", insertable = false, updatable = false)
    private Tower tower;
}
