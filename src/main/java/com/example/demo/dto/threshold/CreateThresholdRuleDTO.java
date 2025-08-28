package com.example.demo.dto.threshold;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateThresholdRuleDTO {
    
    @NotBlank(message = "Rule name is required")
    private String name;
    
    @NotBlank(message = "Metric is required")
    private String metric;
    
    @NotBlank(message = "Condition is required")
    private String condition;
    
    @NotNull(message = "Value is required")
    private Double value;
    
    @NotBlank(message = "Severity is required")
    private String severity;
    
    private Boolean enabled;
    private String description;
    private Long towerId;
}
