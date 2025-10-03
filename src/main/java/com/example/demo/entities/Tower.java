package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "towers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tower_id")
    private Long id;

    @NotBlank(message = "Tower name is required")
    @Size(min = 2, max = 100, message = "Tower name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Tower status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TowerStatus status;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90 degrees")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90 degrees")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180 degrees")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180 degrees")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    @Column(name = "city", nullable = false)
    private String city;

    @Min(value = 0, message = "Battery percentage must be between 0 and 100")
    @Max(value = 100, message = "Battery percentage must be between 0 and 100")
    @Column(name = "battery")
    private Integer battery; // Percentage 0-100

    @DecimalMin(value = "-273.15", message = "Temperature cannot be below absolute zero")
    @DecimalMax(value = "100.0", message = "Temperature cannot exceed 100°C")
    @Column(name = "temperature")
    private Double temperature; // Celsius

    @Min(value = 0, message = "Uptime percentage must be between 0 and 100")
    @Max(value = 100, message = "Uptime percentage must be between 0 and 100")
    @Column(name = "uptime")
    private Integer uptime; // Percentage 0-100

    @Min(value = 0, message = "Network load percentage must be between 0 and 100")
    @Max(value = 100, message = "Network load percentage must be between 0 and 100")
    @Column(name = "network_load")
    private Integer networkLoad; // Percentage 0-100

    @Size(max = 200, message = "Use case description cannot exceed 200 characters")
    @Column(name = "use_case")
    private String useCase;

    @Size(max = 100, message = "Region name cannot exceed 100 characters")
    @Column(name = "region")
    private String region;

    @Column(name = "last_maintenance")
    private LocalDate lastMaintenance;

    @Size(max = 500, message = "3D model path cannot exceed 500 characters")
    @Column(name = "model_3d_path")
    private String model3dPath; // Path to 3D model file (e.g., "/models/tower1.glb")

    @Size(max = 500, message = "API endpoint URL cannot exceed 500 characters")
    @Column(name = "api_endpoint_url")
    private String apiEndpointUrl; // External API endpoint for telemetry data

    @Size(max = 200, message = "API key cannot exceed 200 characters")
    @Column(name = "api_key")
    private String apiKey; // API key for authentication

    // Preferred live refresh interval in milliseconds
    @Column(name = "refresh_interval_ms")
    private Integer refreshIntervalMs;

    // SiteBoss Integration fields
    @Column(name = "siteboss_enabled")
    private Boolean sitebossEnabled = false;

    @Size(max = 255, message = "SiteBoss host cannot exceed 255 characters")
    @Column(name = "siteboss_host")
    private String sitebossHost;

    @Size(max = 100, message = "SiteBoss username cannot exceed 100 characters")
    @Column(name = "siteboss_username")
    private String sitebossUsername;

    @Size(max = 100, message = "SiteBoss password cannot exceed 100 characters")
    @Column(name = "siteboss_password")
    private String sitebossPassword;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Reverse relationships with cascade operations - using Set instead of List
    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hardware> hardware = new HashSet<>();

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Alert> alerts = new HashSet<>();

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThresholdRule> thresholdRules = new HashSet<>();

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TelemetryData> telemetryData = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // Custom validation method for business logic
    public boolean isValidCoordinates() {
        return latitude != null && longitude != null &&
               latitude >= -90.0 && latitude <= 90.0 &&
               longitude >= -180.0 && longitude <= 180.0;
    }

    public boolean isValidBattery() {
        return battery == null || (battery >= 0 && battery <= 100);
    }

    public boolean isValidTemperature() {
        return temperature == null || (temperature >= -273.15 && temperature <= 100.0);
    }

    public boolean isValidUptime() {
        return uptime == null || (uptime >= 0 && uptime <= 100);
    }

    public boolean isValidNetworkLoad() {
        return networkLoad == null || (networkLoad >= 0 && networkLoad <= 100);
    }
}