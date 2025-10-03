package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SiteBossService {

    @Value("${siteboss.host:10.9.1.19}")
    private String siteBossHost;

    @Value("${siteboss.username:admin}")
    private String siteBossUsername;

    @Value("${siteboss.password:password}")
    private String siteBossPassword;

    @Value("${siteboss.script.path:/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss_api.py}")
    private String scriptPath;

    @Value("${siteboss.output.path:/Users/mac/Desktop/PFE/Project/siteboss-project/backend_data.json}")
    private String outputPath;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MeterRegistry meterRegistry;
    private final Timer pullTimer;

    // Holders for dynamic gauges (per tower, per sensor)
    private final ConcurrentHashMap<String, AtomicReference<Double>> sitebossGaugeHolders = new ConcurrentHashMap<>();

    private AtomicReference<Double> getOrCreateGaugeHolder(String name, Tags tags) {
        String key = name + "|" + tags.toString();
        return sitebossGaugeHolders.computeIfAbsent(key, k -> {
            AtomicReference<Double> ref = new AtomicReference<>(null);
            Gauge.builder(name, ref, r -> {
                        Double v = r.get();
                        return v == null ? Double.NaN : v;
                    })
                    .tags(tags)
                    .register(meterRegistry);
            return ref;
        });
    }

    public SiteBossService(@Value("${siteboss.host:10.9.1.19}") String host,
                           @Value("${siteboss.username:admin}") String user,
                           @Value("${siteboss.password:password}") String pass,
                           @Value("${siteboss.script.path:/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss_api.py}") String script,
                           @Value("${siteboss.output.path:/Users/mac/Desktop/PFE/Project/siteboss-project/backend_data.json}") String out,
                           MeterRegistry meterRegistry) {
        this.siteBossHost = host;
        this.siteBossUsername = user;
        this.siteBossPassword = pass;
        this.scriptPath = script;
        this.outputPath = out;
        this.meterRegistry = meterRegistry;
        this.pullTimer = Timer.builder("siteboss_pull_duration_seconds")
                .description("Duration of SiteBoss pulls")
                .register(meterRegistry);
    }

    /**
     * Pull data from SiteBoss device asynchronously
     */
    public CompletableFuture<Map<String, Object>> pullSiteBossDataAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("🚀 Starting SiteBoss data pull for host: {}", siteBossHost);
                meterRegistry.counter("siteboss_pull_started_total").increment();
                
            // Execute the Python script using the virtual environment Python
            ProcessBuilder processBuilder = new ProcessBuilder(
                "/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss-venv/bin/python", 
                scriptPath,
                "--host", siteBossHost,
                "--user", siteBossUsername,
                "--pass", siteBossPassword,
                "--output", outputPath
            );
                
            // Set working directory to the siteboss-project folder
            processBuilder.directory(new File("/Users/mac/Desktop/PFE/Project/siteboss-project"));
            
            // Set environment variables for the virtual environment
            Map<String, String> env = processBuilder.environment();
            String venvPath = "/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss-venv/bin";
            env.put("PATH", venvPath + ":" + env.get("PATH"));
                
                Process process = processBuilder.start();
                
                // Read output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    log.info("SiteBoss: {}", line);
                }
                
                // Read errors
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorOutput = new StringBuilder();
                while ((line = errorReader.readLine()) != null) {
                    errorOutput.append(line).append("\n");
                    log.warn("SiteBoss Error: {}", line);
                }
                
                long startNanos = System.nanoTime();
                int exitCode = process.waitFor();
                pullTimer.record(System.nanoTime() - startNanos, java.util.concurrent.TimeUnit.NANOSECONDS);
                
                if (exitCode == 0) {
                    log.info("SiteBoss data pull completed successfully");
                    meterRegistry.counter("siteboss_pull_success_total").increment();
                    return parseSiteBossData();
                } else {
                    log.error("SiteBoss data pull failed with exit code: {}", exitCode);
                    log.error("Error output: {}", errorOutput.toString());
                    meterRegistry.counter("siteboss_pull_errors_total").increment();
                    return createErrorResponse("SiteBoss data pull failed with exit code: " + exitCode);
                }
                
            } catch (Exception e) {
                log.error("Exception during SiteBoss data pull", e);
                meterRegistry.counter("siteboss_pull_errors_total").increment();
                return createErrorResponse("Exception during SiteBoss data pull: " + e.getMessage());
            }
        });
    }

    /**
     * Pull data from SiteBoss device synchronously
     */
    public Map<String, Object> pullSiteBossData() {
        try {
            log.info("Starting synchronous SiteBoss data pull for host: {}", siteBossHost);
            
            meterRegistry.counter("siteboss_pull_started_total").increment();
            // Execute the Python script using the virtual environment Python
            ProcessBuilder processBuilder = new ProcessBuilder(
                "/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss-venv/bin/python", 
                scriptPath,
                "--host", siteBossHost,
                "--user", siteBossUsername,
                "--pass", siteBossPassword,
                "--output", outputPath
            );
            
            // Set working directory to the siteboss-project folder
            processBuilder.directory(new File("/Users/mac/Desktop/PFE/Project/siteboss-project"));
            
            // Set environment variables for the virtual environment
            Map<String, String> env = processBuilder.environment();
            String venvPath = "/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss-venv/bin";
            env.put("PATH", venvPath + ":" + env.get("PATH"));
            
            Process process = processBuilder.start();
            
            // Read output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                log.info("SiteBoss: {}", line);
            }
            
            long startNanos = System.nanoTime();
            int exitCode = process.waitFor();
            pullTimer.record(System.nanoTime() - startNanos, java.util.concurrent.TimeUnit.NANOSECONDS);
            
            if (exitCode == 0) {
                log.info("SiteBoss data pull completed successfully");
                meterRegistry.counter("siteboss_pull_success_total").increment();
                return parseSiteBossData();
            } else {
                log.error("SiteBoss data pull failed with exit code: {}", exitCode);
                meterRegistry.counter("siteboss_pull_errors_total").increment();
                return createErrorResponse("SiteBoss data pull failed with exit code: " + exitCode);
            }
            
        } catch (Exception e) {
            log.error("Exception during SiteBoss data pull", e);
            meterRegistry.counter("siteboss_pull_errors_total").increment();
            return createErrorResponse("Exception during SiteBoss data pull: " + e.getMessage());
        }
    }

    /**
     * Parse the JSON data from SiteBoss
     */
    private Map<String, Object> parseSiteBossData() {
        try {
            Path jsonPath = Paths.get(outputPath);
            if (!Files.exists(jsonPath)) {
                log.error("SiteBoss JSON output file not found: {}", outputPath);
                return createErrorResponse("SiteBoss JSON output file not found");
            }
            
            String jsonContent = Files.readString(jsonPath);
            JsonNode jsonNode = objectMapper.readTree(jsonContent);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("timestamp", Instant.now().toString());
            result.put("data", jsonNode);
            
            // Extract key metrics for quick access
            if (jsonNode.has("unit")) {
                JsonNode unit = jsonNode.get("unit");
                result.put("siteName", unit.path("siteName").asText("Unknown"));
                result.put("serial", unit.path("serial").asText("Unknown"));
                result.put("uptime", unit.path("uptime").asText("Unknown"));
            }
            
            if (jsonNode.has("sensors")) {
                result.put("sensorCount", jsonNode.get("sensors").size());
            }
            
            if (jsonNode.has("alerts")) {
                result.put("alertCount", jsonNode.get("alerts").size());
            }
            
            log.info("Successfully parsed SiteBoss data");
            return result;
            
        } catch (Exception e) {
            log.error("Error parsing SiteBoss JSON data", e);
            return createErrorResponse("Error parsing SiteBoss JSON data: " + e.getMessage());
        }
    }

    /**
     * Create error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("timestamp", Instant.now().toString());
        errorResponse.put("error", message);
        return errorResponse;
    }

    /**
     * Get the latest SiteBoss data without pulling
     * This now reads from the most recently updated tower file
     */
    public Map<String, Object> getLatestSiteBossData() {
        try {
            // Try to find the most recent tower-specific file
            File sitebossDir = new File("/Users/mac/Desktop/PFE/Project/siteboss-project");
            File[] towerFiles = sitebossDir.listFiles((dir, name) -> name.startsWith("backend_data_") && name.endsWith(".json"));
            
            File mostRecentFile = null;
            long mostRecentTime = 0;
            
            if (towerFiles != null && towerFiles.length > 0) {
                for (File file : towerFiles) {
                    if (file.lastModified() > mostRecentTime) {
                        mostRecentTime = file.lastModified();
                        mostRecentFile = file;
                    }
                }
            }
            
            // Fallback to the default file if no tower files found
            if (mostRecentFile == null) {
                Path jsonPath = Paths.get(outputPath);
                if (!Files.exists(jsonPath)) {
                    return createErrorResponse("No SiteBoss data available");
                }
                return parseSiteBossData();
            }
            
            // Read from the most recent tower file
            return parseSiteBossDataFromPath(mostRecentFile.getAbsolutePath());
            
        } catch (Exception e) {
                log.error("Error reading latest SiteBoss data", e);
            return createErrorResponse("Error reading latest SiteBoss data: " + e.getMessage());
        }
    }

    /**
     * Pull data for a specific tower using its SiteBoss credentials, with per-tower metrics labels.
     */
    public CompletableFuture<Map<String, Object>> pullForTowerAsync(Long towerId,
                                                                    String host,
                                                                    String user,
                                                                    String pass) {
        final String towerIdStr = String.valueOf(towerId);
        final String perTowerOutput = "/Users/mac/Desktop/PFE/Project/siteboss-project/backend_data_" + towerIdStr + ".json";

        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("🚀 Starting SiteBoss data pull for tower {} host: {}", towerIdStr, host);
                meterRegistry.counter("siteboss_pull_started_total", "towerId", towerIdStr).increment();

                ProcessBuilder processBuilder = new ProcessBuilder(
                    "/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss-venv/bin/python",
                    scriptPath,
                    "--host", host,
                    "--user", user,
                    "--pass", pass,
                    "--output", perTowerOutput
                );

                processBuilder.directory(new File("/Users/mac/Desktop/PFE/Project/siteboss-project"));
                Map<String, String> env = processBuilder.environment();
                String venvPath = "/Users/mac/Desktop/PFE/Project/siteboss-project/siteboss-venv/bin";
                env.put("PATH", venvPath + ":" + env.get("PATH"));

                long startNanos = System.nanoTime();
                Process process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                     BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        log.info("SiteBoss[{}]: {}", towerIdStr, line);
                    }
                    while ((line = errorReader.readLine()) != null) {
                        log.warn("SiteBoss Error[{}]: {}", towerIdStr, line);
                    }
                }

                int exitCode = process.waitFor();
                meterRegistry.timer("siteboss_pull_duration_seconds", "towerId", towerIdStr)
                        .record(System.nanoTime() - startNanos, java.util.concurrent.TimeUnit.NANOSECONDS);

                if (exitCode == 0) {
                    log.info("SiteBoss data pull completed successfully for tower {}", towerIdStr);
                    meterRegistry.counter("siteboss_pull_success_total", "towerId", towerIdStr).increment();
                    Map<String, Object> parsed = parseSiteBossDataFromPath(perTowerOutput);
                    try {
                        // Update gauges for dashboard consumption
                        Object sc = parsed.get("sensorCount");
                        if (sc instanceof Number) {
                            meterRegistry.gauge("siteboss_sensor_count", Tags.of("towerId", towerIdStr), ((Number) sc).doubleValue());
                        }
                        Object data = parsed.get("data");
                        if (data instanceof JsonNode) {
                            JsonNode node = (JsonNode) data;
                            String siteName = node.path("unit").path("siteName").asText(null);
                            String serial = node.path("unit").path("serial").asText(null);
                            String lastUpdatedIso = node.path("unit").path("timestamp").path("lastUpdated").asText(null);
                            if (lastUpdatedIso != null && !lastUpdatedIso.isEmpty()) {
                                try {
                                    Instant du = Instant.parse(lastUpdatedIso);
                                    meterRegistry.gauge("siteboss_device_time_epoch_seconds", Tags.of("towerId", towerIdStr), (double) du.getEpochSecond());
                                } catch (Exception ignored) {}
                            }
                            // Info metric encoded via labels
                            meterRegistry.gauge(
                                    "siteboss_unit_info",
                                    Tags.of("towerId", towerIdStr,
                                            "serial", serial == null ? "unknown" : serial,
                                            "siteName", siteName == null ? "unknown" : siteName),
                                    1.0);

                            // Export numeric sensor values as gauges
                            Pattern numPattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
                            JsonNode sensors = node.path("sensors");
                            int normalCount = 0, warningCount = 0, criticalCount = 0;
                            if (sensors.isArray()) {
                                for (JsonNode s : sensors) {
                                    String sid = s.path("id").asText(null);
                                    String sname = s.path("name").asText(null);
                                    String valueStr = s.path("value").asText("");
                                    String units = s.path("units").asText("");
                                    String level = s.path("alertLevel").asText("");
                                    if ("warning".equalsIgnoreCase(level)) warningCount++;
                                    else if ("critical".equalsIgnoreCase(level)) criticalCount++;
                                    else normalCount++;
                                    Matcher m = numPattern.matcher(valueStr);
                                    if (m.find()) {
                                        try {
                                            double val = Double.parseDouble(m.group());
                                            Tags tags = Tags.of("towerId", towerIdStr,
                                                    "sensorId", sid == null ? "unknown" : sid,
                                                    "name", sname == null ? "unknown" : sname,
                                                    "units", units == null ? "" : units);
                                            AtomicReference<Double> holder = getOrCreateGaugeHolder("siteboss_sensor_value", tags);
                                            holder.set(val);
                                        } catch (Exception ignore) {}
                                    }
                                }
                                meterRegistry.gauge("siteboss_alert_count", Tags.of("towerId", towerIdStr, "level", "normal"), (double) normalCount);
                                meterRegistry.gauge("siteboss_alert_count", Tags.of("towerId", towerIdStr, "level", "warning"), (double) warningCount);
                                meterRegistry.gauge("siteboss_alert_count", Tags.of("towerId", towerIdStr, "level", "critical"), (double) criticalCount);
                            }
                        }
                        meterRegistry.gauge("siteboss_last_pull_epoch_seconds", Tags.of("towerId", towerIdStr), (double) Instant.now().getEpochSecond());
                    } catch (Exception ignore) { }
                    return parsed;
                } else {
                    log.error("SiteBoss data pull failed with exit code: {} for tower {}", exitCode, towerIdStr);
                    meterRegistry.counter("siteboss_pull_errors_total", "towerId", towerIdStr).increment();
                    return createErrorResponse("SiteBoss data pull failed with exit code: " + exitCode);
                }
            } catch (Exception e) {
                log.error("Exception during SiteBoss data pull for tower {}", towerId, e);
                meterRegistry.counter("siteboss_pull_errors_total", "towerId", String.valueOf(towerId)).increment();
                return createErrorResponse("Exception during SiteBoss data pull: " + e.getMessage());
            }
        });
    }

    /**
     * Get latest cached SiteBoss data for a specific tower
     */
    public Map<String, Object> getLatestForTower(Long towerId) {
        final String path = "/Users/mac/Desktop/PFE/Project/siteboss-project/backend_data_" + towerId + ".json";
        return parseSiteBossDataFromPath(path);
    }

    private Map<String, Object> parseSiteBossDataFromPath(String customPath) {
        try {
            Path jsonPath = Paths.get(customPath);
            if (!Files.exists(jsonPath)) {
                return createErrorResponse("No SiteBoss data available");
            }
            String jsonContent = Files.readString(jsonPath);
            JsonNode jsonNode = objectMapper.readTree(jsonContent);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("timestamp", Instant.now().toString());
            result.put("data", jsonNode);
            if (jsonNode.has("unit")) {
                JsonNode unit = jsonNode.get("unit");
                result.put("siteName", unit.path("siteName").asText("Unknown"));
                result.put("serial", unit.path("serial").asText("Unknown"));
                result.put("uptime", unit.path("uptime").asText("Unknown"));
            }
            if (jsonNode.has("sensors")) {
                result.put("sensorCount", jsonNode.get("sensors").size());
            }
            return result;
        } catch (Exception e) {
            log.error("Error parsing SiteBoss JSON data from {}", customPath, e);
            return createErrorResponse("Error parsing SiteBoss JSON data: " + e.getMessage());
        }
    }
}
