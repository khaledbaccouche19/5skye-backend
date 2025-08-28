package com.example.demo.dto.threshold;

import lombok.Data;
import lombok.Builder;
import java.time.Instant;

@Data
@Builder
public class ThresholdRuleDTO {
    private Long id;
    private String name;
    private String metric;
    private String condition;
    private Double value;
    private String severity;
    private Boolean enabled;
    private String description;
    private Long towerId;
    private Instant createdAt;
    private Instant updatedAt;
}
