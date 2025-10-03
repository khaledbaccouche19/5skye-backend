package com.example.demo.dto.tower;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CreateTowerDTO {
    
    @NotBlank(message = "Tower name is required")
    @Size(min = 2, max = 100, message = "Tower name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(ONLINE|OFFLINE|WARNING|CRITICAL|DEACTIVATED)$", 
             message = "Status must be one of: ONLINE, OFFLINE, WARNING, CRITICAL, DEACTIVATED")
    private String status;
    
    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90 degrees")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90 degrees")
    private Double latitude;
    
    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180 degrees")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180 degrees")
    private Double longitude;
    
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    private String city;
    
    @Min(value = 0, message = "Battery percentage must be between 0 and 100")
    @Max(value = 100, message = "Battery percentage must be between 0 and 100")
    private Integer battery;
    
    @DecimalMin(value = "-273.15", message = "Temperature cannot be below absolute zero")
    @DecimalMax(value = "100.0", message = "Temperature cannot exceed 100°C")
    private Double temperature;
    
    @Min(value = 0, message = "Uptime percentage must be between 0 and 100")
    @Max(value = 100, message = "Uptime percentage must be between 0 and 100")
    private Integer uptime;
    
    @Min(value = 0, message = "Network load percentage must be between 0 and 100")
    @Max(value = 100, message = "Network load percentage must be between 0 and 100")
    private Integer networkLoad;
    
    @Size(max = 200, message = "Use case description cannot exceed 200 characters")
    private String useCase;
    
    @Size(max = 100, message = "Region name cannot exceed 100 characters")
    private String region;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Last maintenance date must be in YYYY-MM-DD format")
    private String lastMaintenance;
    
    @Size(max = 500, message = "3D model path cannot exceed 500 characters")
    private String model3dPath; // Optional 3D model path
    
    @Size(max = 500, message = "API endpoint URL cannot exceed 500 characters")
    private String apiEndpointUrl; // Optional external API endpoint for telemetry data
    
    @Size(max = 200, message = "API key cannot exceed 200 characters")
    private String apiKey; // Optional API key for authentication
    
    // Preferred live refresh interval in milliseconds (optional)
    @Min(value = 500, message = "Refresh interval must be at least 500 ms")
    @Max(value = 600000, message = "Refresh interval must be <= 600000 ms")
    private Integer refreshIntervalMs;

    // SiteBoss Integration fields (optional)
    private Boolean sitebossEnabled;
    
    @Size(max = 255, message = "SiteBoss host cannot exceed 255 characters")
    private String sitebossHost;
    
    @Size(max = 100, message = "SiteBoss username cannot exceed 100 characters")
    private String sitebossUsername;
    
    @Size(max = 100, message = "SiteBoss password cannot exceed 100 characters")
    private String sitebossPassword;
} 