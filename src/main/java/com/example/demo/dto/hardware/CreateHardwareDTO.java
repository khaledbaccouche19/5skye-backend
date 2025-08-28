package com.example.demo.dto.hardware;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateHardwareDTO {
    
    @NotBlank(message = "Hardware name is required")
    private String name;
    
    @NotBlank(message = "Hardware type is required")
    private String type;
    
    @NotBlank(message = "Vendor is required")
    private String vendor;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    @NotBlank(message = "Serial number is required")
    private String serialNumber;
    
    private String warrantyExpiry;
    private String status;
    private String installDate;
    private String specifications;
    private Long towerId;
}
