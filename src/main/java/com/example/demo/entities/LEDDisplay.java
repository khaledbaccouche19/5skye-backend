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
@Table(name = "led_displays")
public class LEDDisplay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long displayId;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private Double pixelPitch; // mm

    @Column(nullable = false)
    private Integer brightness; // nits

    @Column(nullable = false)
    private String resolution;

    @Enumerated(EnumType.STRING)
    private DisplayStatus status; // ACTIVE, INACTIVE, FAULT, MAINTENANCE

    @Column(nullable = false)
    private Boolean isActive;

    private String manufacturer;
    private String model;
    private String serialNumber;
    private String lampType; // SMD, etc.
    private Integer lifespan; // hours
    private String colorCalibration;
    private Integer grayScale;
    private String brightnessControl;
    private String moduleSize;
    private String refreshFrequency;
    private String moduleResolution;
    private String driveMethod;
    private String moduleMaterial;
    private String moduleType;
    private String pcbType;
    private String pcbCoating;
    private String panelMaterial;
    private String panelPaint;
    private String protectionLevel;
    private String viewingAngle;
    private String minimumViewingDistance;
    private String operatingTemperature;
    private String storageTemperature;
    private String operatingHumidity;
    private String powerInput;
    private String powerFrequency;
    private String averagePower;
    private String maximumPower;
    private String coolingMethod;
    private String maxInputResolution;
    private String videoSources;
    private String brightnessAdjustment;
    private String deadPixelMonitoring;
    private String moduleMonitoring;
    private String temperatureMonitoring;
    private String smokeMonitoring;
    private String voltageMonitoring;
    private String signalPathBackup;
    private String signalTransmission;
    private String standbyScheduling;
    private String powerScheduling;
    private String powerSavingMode;
    private String certifications;
    private String ledBrand;
    private String ledSurfaceColor;
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

    @OneToMany(mappedBy = "display", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DisplayMetrics> displayMetrics = new ArrayList<>();



}