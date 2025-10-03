package com.example.demo.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponseDTO {
    private List<Double> forecast;
    private List<Double> q10;
    private List<Double> q90;
    private List<Double> residuals;
    private String severity;
}


