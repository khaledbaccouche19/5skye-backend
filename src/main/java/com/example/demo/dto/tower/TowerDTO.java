package com.example.demo.dto.tower;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
public class TowerDTO {
    private Long id;
    private String name;
    private String status;
    private Double latitude;
    private Double longitude;
    private String city;
    private Integer battery;
    private Double temperature;
    private Integer uptime;
    private Integer networkLoad;
    private String useCase;
    private String region;
    private LocalDate lastMaintenance;
    private String model3dPath; // 3D model path for the tower
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
