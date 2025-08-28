package com.example.demo.dto.hardware;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class HardwareSummaryDTO {
    private Long id;
    private String name;
    private String type;
    private String status;
}
