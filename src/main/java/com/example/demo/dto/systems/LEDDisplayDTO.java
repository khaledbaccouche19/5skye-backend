package com.example.demo.dto.systems;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LEDDisplayDTO {
    private Long id;
    private String displayName;
    private Double pixelPitch;
    private Integer brightness;
    private String resolution;
    private String status;
} 