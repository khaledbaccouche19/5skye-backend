package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "towers")
public class Tower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long towerId;

    @Column(nullable = false)
    private String towerName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private TowerStatus status; // ACTIVE, INACTIVE, MAINTENANCE, FAULT

    @Column(nullable = false)
    private LocalDateTime installationDate;

    private String municipality;
    private String address;
    private String serialNumber;
    private String manufacturer;
    private String model;
    private Integer totalHeight;
    private Integer totalWeight;
    private Boolean isFullyLoaded;
    private String description;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    private String warrantyExpiryDate;
    private String installationCompany;
    private String operatorCompany;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TowerUnit> units = new ArrayList<>();

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaintenanceSchedule> maintenanceSchedules = new ArrayList<>();

    @ManyToMany(mappedBy = "accessibleTowers")
    private List<User> authorizedUsers = new ArrayList<>();



}