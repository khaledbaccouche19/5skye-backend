package com.example.demo.dto.hardware;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class HardwareDTO {
    private Long id;
    private String name;
    private String type;
    private String vendor;
    private String model;
    private String serialNumber;
    private String warrantyExpiry;
    private String status;
    private String installDate;
    private String specifications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long towerId;
}
