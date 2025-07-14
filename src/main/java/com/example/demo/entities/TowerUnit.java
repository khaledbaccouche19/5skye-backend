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
@Table(name = "tower_units")
public class TowerUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    @Enumerated(EnumType.STRING)
    private UnitType unitType; // BASE, AUXILIARY, DISPLAY, VENTILATION, ANTENNA

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Integer diameter;

    @Enumerated(EnumType.STRING)
    private UnitStatus status; // ACTIVE, INACTIVE, MAINTENANCE, FAULT

    @Column(nullable = false)
    private Integer stackPosition; // Order from bottom (1) to top

    private String serialNumber;
    private String manufacturer;
    private String model;
    private String description;
    private LocalDateTime installationDate;
    private String installationNotes;
    private Boolean isRemovable;
    private String color;
    private String material;
    private Double maxLoadCapacity;
    private String weatherResistance;
    private String certifications;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tower tower;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipment> equipment = new ArrayList<>();


}