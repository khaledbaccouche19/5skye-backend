package com.example.demo.dto.alert;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateAlertDTO {
    
    @NotBlank(message = "Alert message is required")
    private String message;
    
    @NotBlank(message = "Alert severity is required")
    private String severity;
    
    @NotNull(message = "Tower ID is required")
    private Long towerId;
    
    private String towerName;
    private Boolean resolved;
} 