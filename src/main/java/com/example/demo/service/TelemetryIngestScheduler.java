package com.example.demo.service;

import com.example.demo.dto.telemetry.CreateTelemetryDataDTO;
import com.example.demo.entities.Tower;
import com.example.demo.repositories.TelemetryDataRepository;
import com.example.demo.repositories.TowerRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

@Component
public class TelemetryIngestScheduler {

    private final TowerRepository towerRepository;
    private final TelemetryDataRepository telemetryDataRepository;
    private final TelemetryDataService telemetryDataService;
    private final MeterRegistry meterRegistry;
    private final Timer ingestTimer;

    private final RestTemplate restTemplate = new RestTemplate();

    // Holders for simulator dynamic gauges (towerId + metric name)
    private final ConcurrentHashMap<String, AtomicReference<Double>> simulatorGaugeHolders = new ConcurrentHashMap<>();

    private AtomicReference<Double> getOrCreateSimGauge(String metricName, Tags tags) {
        String key = metricName + "|" + tags.toString();
        return simulatorGaugeHolders.computeIfAbsent(key, k -> {
            AtomicReference<Double> ref = new AtomicReference<>(null);
            Gauge.builder(metricName, ref, r -> {
                        Double v = r.get();
                        return v == null ? Double.NaN : v;
                    })
                    .tags(tags)
                    .register(meterRegistry);
            return ref;
        });
    }

    public TelemetryIngestScheduler(TowerRepository towerRepository,
                                    TelemetryDataRepository telemetryDataRepository,
                                    TelemetryDataService telemetryDataService,
                                    MeterRegistry meterRegistry) {
        this.towerRepository = towerRepository;
        this.telemetryDataRepository = telemetryDataRepository;
        this.telemetryDataService = telemetryDataService;
        this.meterRegistry = meterRegistry;
        this.ingestTimer = Timer.builder("telemetry_ingest_duration_seconds")
                .description("Duration of telemetry ingest job")
                .register(meterRegistry);
    }

    // Ingest every 30s
    @Scheduled(fixedDelay = 30000, initialDelay = 10000)
    public void ingestLiveSnapshots() {
        long startNanos = System.nanoTime();
        List<Tower> towers = towerRepository.findAll();
        for (Tower tower : towers) {
            if (tower.getApiEndpointUrl() == null || tower.getApiEndpointUrl().isEmpty()) {
                continue;
            }
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                if (tower.getApiKey() != null && !tower.getApiKey().isEmpty()) {
                    headers.set("Authorization", "Bearer " + tower.getApiKey());
                }

                ResponseEntity<Object> response = restTemplate.exchange(
                        tower.getApiEndpointUrl(), HttpMethod.GET, new HttpEntity<>(headers), Object.class);

                Object body = response.getBody();
                if (body instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> items = (List<Map<String, Object>>) body;
                    if (!items.isEmpty()) {
                        Map<String, Object> latest = items.get(0);
                        persistSnapshot(tower.getId(), latest);
                    }
                } else if (body instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> latest = (Map<String, Object>) body;
                    persistSnapshot(tower.getId(), latest);
                }
            } catch (Exception ignored) {
                meterRegistry.counter("telemetry_ingest_errors_total", "towerId", String.valueOf(tower.getId())).increment();
            }
        }
        ingestTimer.record(System.nanoTime() - startNanos, java.util.concurrent.TimeUnit.NANOSECONDS);
        meterRegistry.counter("telemetry_ingest_runs_total").increment();
    }

    private void persistSnapshot(Long towerId, Map<String, Object> src) {
        CreateTelemetryDataDTO dto = new CreateTelemetryDataDTO();
        dto.setTowerId(towerId);
        // timestamp fallback to now if absent
        Object ts = src.get("timestamp");
        dto.setTimestamp(ts instanceof String ? Instant.parse((String) ts) : Instant.now());
        dto.setStatus(asString(src.get("status")));
        dto.setBattery(asInteger(src.get("battery")));
        dto.setUptime(asDouble(src.get("uptime")));
        dto.setTemperature(asDouble(src.get("temperature")));
        dto.setAmbientTemperature(asDouble(src.get("ambientTemperature")));
        dto.setHumidity(asDouble(src.get("humidity")));
        dto.setWindSpeed(asDouble(src.get("windSpeed")));
        dto.setWindDirection(asInteger(src.get("windDirection")));
        dto.setAirQuality(asInteger(src.get("airQuality")));
        dto.setUvIndex(asDouble(src.get("uvIndex")));
        dto.setPressure(asDouble(src.get("pressure")));
        dto.setPrecipitation(asDouble(src.get("precipitation")));
        dto.setNetworkLoad(asDouble(src.get("networkLoad")));
        dto.setSignalStrength(asDouble(src.get("signalStrength")));
        dto.setLatency(asDouble(src.get("latency")));
        dto.setPacketLoss(asDouble(src.get("packetLoss")));
        dto.setJitter(asDouble(src.get("jitter")));
        dto.setBandwidth(asDouble(src.get("bandwidth")));
        dto.setThroughput(asDouble(src.get("throughput")));
        dto.setResponseTime(asDouble(src.get("responseTime")));
        dto.setInterference(asDouble(src.get("interference")));
        dto.setCpuUtilization(asDouble(src.get("cpuUtilization")));
        dto.setMemoryUsage(asDouble(src.get("memoryUsage")));
        dto.setDiskSpace(asDouble(src.get("diskSpace")));
        dto.setErrorRate(asDouble(src.get("errorRate")));
        dto.setVibration(asDouble(src.get("vibration")));
        dto.setVoltage(asDouble(src.get("voltage")));

        // Simple dedupe: skip if an entry already exists with same towerId and timestamp
        boolean exists = !telemetryDataRepository
                .findByTowerIdAndTimestampBetweenOrderByTimestampAsc(towerId, dto.getTimestamp(), dto.getTimestamp())
                .isEmpty();
        if (!exists) {
            telemetryDataService.createTelemetryData(dto);
            meterRegistry.counter("telemetry_ingest_saved_total", "towerId", String.valueOf(towerId)).increment();
        }

        // Export a subset of simulator metrics as gauges for Grafana (latest values)
        String tId = String.valueOf(towerId);
        publishSimGauge(tId, "simulator_metric_value", "temperature", dto.getTemperature(), "C");
        publishSimGauge(tId, "simulator_metric_value", "ambient_temperature", dto.getAmbientTemperature(), "C");
        publishSimGauge(tId, "simulator_metric_value", "humidity", dto.getHumidity(), "%");
        publishSimGauge(tId, "simulator_metric_value", "battery", dto.getBattery(), "%");
        publishSimGauge(tId, "simulator_metric_value", "network_load", dto.getNetworkLoad(), "%");
        publishSimGauge(tId, "simulator_metric_value", "uptime", dto.getUptime(), "%");
        publishSimGauge(tId, "simulator_metric_value", "wind_speed", dto.getWindSpeed(), "mps");
        publishSimGauge(tId, "simulator_metric_value", "signal_strength", dto.getSignalStrength(), "dBm");
        publishSimGauge(tId, "simulator_metric_value", "latency", dto.getLatency(), "ms");
        publishSimGauge(tId, "simulator_metric_value", "packet_loss", dto.getPacketLoss(), "%");
        publishSimGauge(tId, "simulator_metric_value", "jitter", dto.getJitter(), "ms");
        publishSimGauge(tId, "simulator_metric_value", "bandwidth", dto.getBandwidth(), "Mbps");
        publishSimGauge(tId, "simulator_metric_value", "throughput", dto.getThroughput(), "Mbps");
        publishSimGauge(tId, "simulator_metric_value", "response_time", dto.getResponseTime(), "ms");
        publishSimGauge(tId, "simulator_metric_value", "cpu_utilization", dto.getCpuUtilization(), "%");
        publishSimGauge(tId, "simulator_metric_value", "memory_usage", dto.getMemoryUsage(), "%");
        publishSimGauge(tId, "simulator_metric_value", "disk_space", dto.getDiskSpace(), "%");
        publishSimGauge(tId, "simulator_metric_value", "voltage", dto.getVoltage(), "V");
        publishSimGauge(tId, "simulator_metric_value", "pressure", dto.getPressure(), "hPa");
        publishSimGauge(tId, "simulator_metric_value", "precipitation", dto.getPrecipitation(), "mm");
        publishSimGauge(tId, "simulator_metric_value", "uv_index", dto.getUvIndex(), "index");
        publishSimGauge(tId, "simulator_metric_value", "air_quality", dto.getAirQuality(), "aqi");
        if (dto.getTimestamp() != null) {
            Gauge.builder("simulator_last_snapshot_epoch_seconds", dto, d -> (double) d.getTimestamp().getEpochSecond())
                    .tags(Tags.of("towerId", tId))
                    .register(meterRegistry);
        }
    }

    private static String asString(Object o) { return o == null ? null : String.valueOf(o); }
    private static Integer asInteger(Object o) {
        if (o == null) return null;
        if (o instanceof Number) return ((Number) o).intValue();
        try { return Integer.parseInt(o.toString()); } catch (Exception e) { return null; }
    }
    private static Double asDouble(Object o) {
        if (o == null) return null;
        if (o instanceof Number) return ((Number) o).doubleValue();
        try { return Double.parseDouble(o.toString()); } catch (Exception e) { return null; }
    }

    private void publishSimGauge(String towerId, String metric, String name, Number value, String units) {
        if (value == null) return;
        Tags tags = Tags.of("towerId", towerId, "name", name, "units", units == null ? "" : units);
        AtomicReference<Double> ref = getOrCreateSimGauge(metric, tags);
        ref.set(value.doubleValue());
    }
}


