package com.example.demo.dto.systems;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BatteryDTO {
    private Long batteryId;
    private String batteryType;
    private Integer capacity;
    private Double voltage;
    private Double currentCharge;
    private String status;
    private String serialNumber;
    private LocalDate installationDate;
} 