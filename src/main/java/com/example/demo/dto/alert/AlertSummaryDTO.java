package com.example.demo.dto.alert;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AlertSummaryDTO {
    private Long id;
    private String message;
    private String severity;
}
