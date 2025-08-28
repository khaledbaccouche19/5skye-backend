package com.example.demo.dto.mapper;

import com.example.demo.dto.hardware.CreateHardwareDTO;
import com.example.demo.dto.hardware.HardwareDTO;
import com.example.demo.dto.hardware.HardwareSummaryDTO;
import com.example.demo.entities.Hardware;
import com.example.demo.entities.HardwareStatus;
import com.example.demo.entities.HardwareType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class HardwareMapper {

    public HardwareDTO toDTO(Hardware hardware) {
        if (hardware == null) {
            return null;
        }

        return HardwareDTO.builder()
                .id(hardware.getId())
                .name(hardware.getName())
                .type(hardware.getType() != null ? hardware.getType().name().toLowerCase() : null)
                .vendor(hardware.getVendor())
                .model(hardware.getModel())
                .serialNumber(hardware.getSerialNumber())
                .warrantyExpiry(hardware.getWarrantyExpiry() != null ? hardware.getWarrantyExpiry().toString() : null)
                .status(hardware.getStatus() != null ? hardware.getStatus().name().toLowerCase() : null)
                .installDate(hardware.getInstallDate() != null ? hardware.getInstallDate().toString() : null)
                .specifications(hardware.getSpecifications())
                .createdAt(hardware.getCreatedAt())
                .updatedAt(hardware.getUpdatedAt())
                .towerId(hardware.getTower() != null ? hardware.getTower().getId() : null)
                .build();
    }

    public Hardware toEntity(CreateHardwareDTO createHardwareDTO) {
        if (createHardwareDTO == null) {
            return null;
        }

        return Hardware.builder()
                .name(createHardwareDTO.getName())
                .type(parseHardwareType(createHardwareDTO.getType()))
                .vendor(createHardwareDTO.getVendor())
                .model(createHardwareDTO.getModel())
                .serialNumber(createHardwareDTO.getSerialNumber())
                .warrantyExpiry(parseLocalDate(createHardwareDTO.getWarrantyExpiry()))
                .status(parseHardwareStatus(createHardwareDTO.getStatus()))
                .installDate(parseLocalDate(createHardwareDTO.getInstallDate()))
                .specifications(createHardwareDTO.getSpecifications())
                .build();
    }

    public HardwareSummaryDTO toSummaryDTO(Hardware hardware) {
        if (hardware == null) {
            return null;
        }

        return HardwareSummaryDTO.builder()
                .id(hardware.getId())
                .name(hardware.getName())
                .type(hardware.getType() != null ? hardware.getType().name().toLowerCase() : null)
                .status(hardware.getStatus() != null ? hardware.getStatus().name().toLowerCase() : null)
                .build();
    }

    private HardwareType parseHardwareType(String type) {
        if (type == null) {
            return null;
        }
        try {
            return HardwareType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return HardwareType.SENSOR;
        }
    }

    private HardwareStatus parseHardwareStatus(String status) {
        if (status == null) {
            return HardwareStatus.ACTIVE;
        }
        try {
            return HardwareStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return HardwareStatus.ACTIVE;
        }
    }

    private LocalDate parseLocalDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return null;
        }
    }
}
