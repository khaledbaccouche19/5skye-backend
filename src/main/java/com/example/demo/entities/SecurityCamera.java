package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "security_cameras")
public class SecurityCamera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cameraId;

    @Column(nullable = false)
    private String cameraName;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private String resolution; // 4K, 1080p, etc.

    @Column(nullable = false)
    private Integer panAngle;

    @Column(nullable = false)
    private Integer tiltAngle;

    @Column(nullable = false)
    private Boolean isRecording;

    private String manufacturer;
    private String model;
    private String serialNumber;
    private String macAddress;
    private String firmwareVersion;
    private String sensorType;
    private String lensType;
    private String compressionFormat; // H.264, H.265, MJPEG
    private Integer maxFrameRate;
    private String nightVisionCapability;
    private String weatherResistance; // IP66, etc.
    private String mountingType;
    private String powerRequirement;
    private String networkProtocols;
    private String streamingProtocols;
    private String audioCapability;
    private String motionDetection;
    private String videoAnalytics;
    private String storageCapacity;
    private String storageType;
    private String encryptionSupport;
    private String accessControl;
    private Integer panRange;
    private Integer tiltRange;
    private Integer zoomRange;
    private String presetPositions;
    private String patrolRoutes;
    private String alarmInputs;
    private String alarmOutputs;
    private LocalDateTime installationDate;
    private LocalDateTime lastMaintenanceDate;
    private String maintenanceNotes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;


}