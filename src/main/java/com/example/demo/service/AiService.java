package com.example.demo.service;

import com.example.demo.dto.ai.AnomalyDTO;
import com.example.demo.dto.ai.PredictiveInsightDTO;
import com.example.demo.dto.ai.ForecastResponseDTO;
import com.example.demo.entities.TelemetryData;
import com.example.demo.repositories.TelemetryDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AiService {

    private final TelemetryDataRepository telemetryDataRepository;
    private final RestTemplate httpClient = new RestTemplate();

    public List<AnomalyDTO> detectAnomalies(Long towerId, Instant startTime, Instant endTime, double zThreshold) {
        List<TelemetryData> window = telemetryDataRepository
                .findByTowerIdAndTimestampBetweenOrderByTimestampAsc(towerId, startTime, endTime);

        List<AnomalyDTO> anomalies = new ArrayList<>();
        if (window == null || window.isEmpty()) {
            return anomalies;
        }

        // Metrics to evaluate. Expand as needed.
        evaluateMetric(anomalies, towerId, window, "temperature", TelemetryData::getTemperature, zThreshold);
        evaluateMetric(anomalies, towerId, window, "battery", td -> td.getBattery() == null ? null : td.getBattery().doubleValue(), zThreshold);
        evaluateMetric(anomalies, towerId, window, "latency", TelemetryData::getLatency, zThreshold);
        evaluateMetric(anomalies, towerId, window, "errorRate", TelemetryData::getErrorRate, zThreshold);
        evaluateMetric(anomalies, towerId, window, "vibration", TelemetryData::getVibration, zThreshold);
        evaluateMetric(anomalies, towerId, window, "throughput", TelemetryData::getThroughput, zThreshold);

        return anomalies;
    }

    /**
     * Generate simple predictive maintenance insights using trend-based heuristics.
     */
    public List<PredictiveInsightDTO> generatePredictiveInsights(Long towerId, Instant startTime, Instant endTime) {
        List<TelemetryData> window = telemetryDataRepository
                .findByTowerIdAndTimestampBetweenOrderByTimestampAsc(towerId, startTime, endTime);

        List<PredictiveInsightDTO> insights = new ArrayList<>();
        if (window == null || window.size() < 5) {
            return insights;
        }

        addBatteryProjection(insights, towerId, window);
        addTemperatureTrend(insights, towerId, window);
        addErrorRateTrend(insights, towerId, window);

        return insights;
    }

    private void addBatteryProjection(List<PredictiveInsightDTO> out, Long towerId, List<TelemetryData> window) {
        List<TelemetryData> points = window.stream().filter(td -> td.getBattery() != null).toList();
        if (points.size() < 5) return;

        LinearFit fit = linearFit(points, TelemetryData::getTimestamp, td -> td.getBattery().doubleValue());
        if (fit == null) return;
        double target = 15.0;
        if (fit.slopePerSecond >= 0) return; // not draining
        double secondsToTarget = (target - fit.intercept) / fit.slopePerSecond; // slope < 0
        if (secondsToTarget <= 0 || secondsToTarget > Duration.ofDays(14).getSeconds()) return;

        Instant predictedBy = fit.origin.plusSeconds((long) secondsToTarget);
        double current = fit.currentValue;
        int confidence = (int) Math.max(60, Math.min(95, 80 - Math.abs(fit.r2Error * 100)));

        out.add(PredictiveInsightDTO.builder()
                .towerId(towerId)
                .title("Battery will reach 15%")
                .recommendation("Schedule battery replacement/charging")
                .type("Predictive Maintenance")
                .riskType("Hardware Failure")
                .urgency(secondsToTarget < Duration.ofDays(3).getSeconds() ? "HIGH" : "WARNING")
                .confidence(confidence)
                .estimatedImpact(0.7)
                .predictedBy(predictedBy)
                .metric("battery")
                .currentValue(current)
                .trendPerHour(fit.slopePerSecond * 3600.0)
                .build());
    }

    private void addTemperatureTrend(List<PredictiveInsightDTO> out, Long towerId, List<TelemetryData> window) {
        List<TelemetryData> points = window.stream().filter(td -> td.getTemperature() != null).toList();
        if (points.size() < 5) return;
        LinearFit fit = linearFit(points, TelemetryData::getTimestamp, TelemetryData::getTemperature);
        if (fit == null || fit.slopePerSecond <= 0) return; // not rising

        double current = fit.currentValue;
        double slopePerHour = fit.slopePerSecond * 3600.0;
        double projected24h = current + slopePerHour * 24.0;
        if (projected24h < 70.0) return; // threshold for concern

        int confidence = (int) Math.max(55, Math.min(90, 75 - Math.abs(fit.r2Error * 100)));
        out.add(PredictiveInsightDTO.builder()
                .towerId(towerId)
                .title("Rising temperature trend may exceed safe levels")
                .recommendation("Inspect cooling/environment controls")
                .type("Predictive Maintenance")
                .riskType("Thermal")
                .urgency(projected24h >= 85.0 ? "HIGH" : "WARNING")
                .confidence(confidence)
                .estimatedImpact(0.6)
                .predictedBy(Instant.now().plus(Duration.ofHours(24)))
                .metric("temperature")
                .currentValue(current)
                .trendPerHour(slopePerHour)
                .build());
    }

    private void addErrorRateTrend(List<PredictiveInsightDTO> out, Long towerId, List<TelemetryData> window) {
        List<TelemetryData> points = window.stream().filter(td -> td.getErrorRate() != null).toList();
        if (points.size() < 5) return;
        LinearFit fit = linearFit(points, TelemetryData::getTimestamp, TelemetryData::getErrorRate);
        if (fit == null || fit.slopePerSecond <= 0) return; // not worsening

        double current = fit.currentValue;
        double slopePerHour = fit.slopePerSecond * 3600.0;
        double projected12h = current + slopePerHour * 12.0;
        if (projected12h < 5.0) return; // 5% threshold

        int confidence = (int) Math.max(55, Math.min(85, 70 - Math.abs(fit.r2Error * 100)));
        out.add(PredictiveInsightDTO.builder()
                .towerId(towerId)
                .title("Error rate trending upward")
                .recommendation("Schedule diagnostics and network checks")
                .type("Predictive Maintenance")
                .riskType("Performance")
                .urgency(projected12h >= 10.0 ? "HIGH" : "WARNING")
                .confidence(confidence)
                .estimatedImpact(0.5)
                .predictedBy(Instant.now().plus(Duration.ofHours(12)))
                .metric("errorRate")
                .currentValue(current)
                .trendPerHour(slopePerHour)
                .build());
    }

    private record LinearFit(Instant origin, double slopePerSecond, double intercept, double currentValue, double r2Error) {}

    private LinearFit linearFit(List<TelemetryData> points,
                                Function<TelemetryData, Instant> timeExtractor,
                                Function<TelemetryData, Double> valueExtractor) {
        Instant t0 = timeExtractor.apply(points.get(0));
        List<double[]> rows = points.stream()
                .map(p -> new double[]{
                        (double) Duration.between(t0, timeExtractor.apply(p)).toSeconds(),
                        valueExtractor.apply(p) != null ? valueExtractor.apply(p) : Double.NaN
                })
                .filter(arr -> !Double.isNaN(arr[1]))
                .toList();
        if (rows.size() < 5) return null;

        double sumT = 0, sumY = 0, sumTT = 0, sumTY = 0;
        for (double[] r : rows) {
            sumT += r[0];
            sumY += r[1];
            sumTT += r[0] * r[0];
            sumTY += r[0] * r[1];
        }
        double n = rows.size();
        double denom = (n * sumTT - sumT * sumT);
        if (denom == 0) return null;
        double slope = (n * sumTY - sumT * sumY) / denom; // per second
        double intercept = (sumY - slope * sumT) / n;

        double ssTot = 0, ssRes = 0;
        double meanY = sumY / n;
        for (double[] r : rows) {
            double yHat = slope * r[0] + intercept;
            ssTot += Math.pow(r[1] - meanY, 2);
            ssRes += Math.pow(r[1] - yHat, 2);
        }
        double r2Error = ssTot == 0 ? 1.0 : (ssRes / ssTot);

        double currentValue = rows.get(rows.size() - 1)[1];
        return new LinearFit(t0, slope, intercept, currentValue, r2Error);
    }

    private void evaluateMetric(
            List<AnomalyDTO> output,
            Long towerId,
            List<TelemetryData> window,
            String metric,
            Function<TelemetryData, Double> extractor,
            double zThreshold
    ) {
        // Collect values, skip nulls
        List<TelemetryData> nonNullPoints = window.stream()
                .filter(td -> extractor.apply(td) != null)
                .toList();
        if (nonNullPoints.size() < 5) {
            return; // not enough data
        }

        // Compute mean and std dev
        DoubleSummaryStatistics stats = nonNullPoints.stream()
                .map(extractor)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        double mean = stats.getAverage();
        double variance = nonNullPoints.stream()
                .map(extractor)
                .filter(Objects::nonNull)
                .mapToDouble(v -> Math.pow(v - mean, 2))
                .sum() / Math.max(1, (nonNullPoints.size() - 1));
        double std = Math.sqrt(variance);
        if (std == 0.0) {
            return; // no variability
        }

        double lower = mean - zThreshold * std;
        double upper = mean + zThreshold * std;

        // Flag points outside bounds
        for (TelemetryData td : nonNullPoints) {
            Double value = extractor.apply(td);
            if (value == null) continue;
            double z = (value - mean) / std;
            if (Math.abs(z) >= zThreshold) {
                output.add(AnomalyDTO.builder()
                        .towerId(towerId)
                        .timestamp(td.getTimestamp())
                        .metric(metric)
                        .value(value)
                        .zScore(z)
                        .severity(deriveSeverity(z))
                        .mean(mean)
                        .stdDev(std)
                        .lowerBound(lower)
                        .upperBound(upper)
                        .build());
            }
        }
    }

    private String deriveSeverity(double zScore) {
        double abs = Math.abs(zScore);
        if (abs >= 3.5) return "CRITICAL";
        if (abs >= 3.0) return "HIGH";
        if (abs >= 2.5) return "WARNING";
        return "INFO";
    }

    public ForecastResponseDTO forecastTemperatureWithChronos(Long towerId, int window, int horizon) {
        // Pull recent temperature values ordered ASC
        Instant end = Instant.now();
        Instant start = end.minusSeconds(window * 60L);
        List<TelemetryData> rows = telemetryDataRepository
                .findByTowerIdAndTimestampBetweenOrderByTimestampAsc(towerId, start, end);
        List<Double> series = rows.stream()
                .map(TelemetryData::getTemperature)
                .filter(Objects::nonNull)
                .toList();
        Map<String, Object> payload = Map.of(
                "series", series,
                "horizon", horizon
        );
        @SuppressWarnings("unchecked")
        Map<String, Object> resp = httpClient.postForObject(
                "http://127.0.0.1:8000/predict",
                payload,
                Map.class
        );
        if (resp == null) {
            return ForecastResponseDTO.builder()
                    .forecast(List.of())
                    .severity("INFO")
                    .build();
        }
        List<Double> forecast = (List<Double>) resp.getOrDefault("forecast", List.of());
        List<Double> q10 = (List<Double>) resp.getOrDefault("q10", null);
        List<Double> q90 = (List<Double>) resp.getOrDefault("q90", null);
        List<Double> residuals = (List<Double>) resp.getOrDefault("residuals", null);
        String severity = (String) resp.getOrDefault("severity", "INFO");
        return ForecastResponseDTO.builder()
                .forecast(forecast)
                .q10(q10)
                .q90(q90)
                .residuals(residuals)
                .severity(severity)
                .build();
    }
}


