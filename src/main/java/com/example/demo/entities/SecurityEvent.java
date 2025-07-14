package com.example.demo.entities;

import jakarta.persistence.*;
import jdk.jfr.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "security_events")
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;




    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventTime;

    @Enumerated(EnumType.STRING)
    private EventSeverity severity; // LOW, MEDIUM, HIGH, CRITICAL

    private String videoFilePath;
    private String imageFilePath;
    private String audioFilePath;
    private String eventLocation;
    private String detectionAlgorithm;
    private Double confidenceScore;
    private String objectsDetected;
    private String faceRecognitionResult;
    private String licensePlateNumber;
    private String behaviorAnalysis;
    private String responseAction;
    private String assignedTo;
    private LocalDateTime acknowledgedAt;
    private LocalDateTime resolvedAt;
    private String resolutionNotes;
    private Boolean isResolved;
    private Boolean requiresFollowUp;
    private String followUpActions;
    private String relatedIncidents;
    private String evidenceCollected;
    private String reportGenerated;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private SecurityCamera camera;
    @ManyToOne(fetch = FetchType.LAZY)

    private User assignedUser;


}