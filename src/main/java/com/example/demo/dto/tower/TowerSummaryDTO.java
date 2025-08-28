package com.example.demo.dto.tower;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TowerSummaryDTO {
    private Long id;
    private String name;
    private String status;
} 