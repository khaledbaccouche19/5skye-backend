package com.example.demo.dto.equipment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentDTO {
    private Long id;
    private String serialNumber;
    private String vendor;
    private String status;
    private String equipmentType;
} 