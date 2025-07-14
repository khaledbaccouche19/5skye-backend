package com.example.demo.dto.systems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerSystemDTO {
    private Long id;
    private String systemType;
    private Double voltage;
    private Double current;
    private String status;
} 