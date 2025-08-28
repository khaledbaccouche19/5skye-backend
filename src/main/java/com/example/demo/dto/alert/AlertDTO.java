package com.example.demo.dto.alert;

import lombok.Data;
import lombok.Builder;
import java.time.Instant;

@Data
@Builder
public class AlertDTO {
    private Long id;
    private Instant timestamp;
    private String message;
    private String severity;
    private Long towerId;
    private String towerName;
    private Boolean resolved;
}
