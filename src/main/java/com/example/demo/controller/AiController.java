package com.example.demo.controller;

import com.example.demo.dto.ai.AnomalyDTO;
import com.example.demo.dto.ai.PredictiveInsightDTO;
import com.example.demo.dto.ai.ForecastResponseDTO;
import com.example.demo.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/towers/{towerId}/anomalies")
    public ResponseEntity<List<AnomalyDTO>> getAnomalies(
            @PathVariable Long towerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
            @RequestParam(required = false, defaultValue = "3.0") double z
    ) {
        Instant effectiveEnd = end != null ? end : Instant.now();
        Instant effectiveStart = start != null ? start : effectiveEnd.minus(60, ChronoUnit.MINUTES);
        // Cap window size to 24h
        if (effectiveStart.isBefore(effectiveEnd.minus(24, ChronoUnit.HOURS))) {
            effectiveStart = effectiveEnd.minus(24, ChronoUnit.HOURS);
        }
        List<AnomalyDTO> anomalies = aiService.detectAnomalies(towerId, effectiveStart, effectiveEnd, z);
        return ResponseEntity.ok(anomalies);
    }

    @GetMapping("/towers/{towerId}/predictions")
    public ResponseEntity<List<PredictiveInsightDTO>> getPredictions(
            @PathVariable Long towerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end
    ) {
        Instant effectiveEnd = end != null ? end : Instant.now();
        Instant effectiveStart = start != null ? start : effectiveEnd.minus(7, ChronoUnit.DAYS);
        if (effectiveStart.isBefore(effectiveEnd.minus(30, ChronoUnit.DAYS))) {
            effectiveStart = effectiveEnd.minus(30, ChronoUnit.DAYS);
        }
        List<PredictiveInsightDTO> insights = aiService.generatePredictiveInsights(towerId, effectiveStart, effectiveEnd);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/towers/{towerId}/forecast/temperature")
    public ResponseEntity<ForecastResponseDTO> forecastTemperature(
            @PathVariable Long towerId,
            @RequestParam(defaultValue = "256") int window,
            @RequestParam(defaultValue = "30") int horizon
    ) {
        ForecastResponseDTO dto = aiService.forecastTemperatureWithChronos(towerId, window, horizon);
        return ResponseEntity.ok(dto);
    }
}


