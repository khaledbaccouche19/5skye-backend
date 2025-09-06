package com.example.demo.dto.mapper;

import com.example.demo.dto.telemetry.CreateTelemetryDataDTO;
import com.example.demo.dto.telemetry.TelemetryDataDTO;
import com.example.demo.entities.TelemetryData;
import org.springframework.stereotype.Component;

@Component
public class TelemetryDataMapper {

    public TelemetryData toEntity(CreateTelemetryDataDTO dto) {
        return TelemetryData.builder()
                .towerId(dto.getTowerId())
                .timestamp(dto.getTimestamp())
                .status(dto.getStatus())
                .battery(dto.getBattery())
                .uptime(dto.getUptime())
                .temperature(dto.getTemperature())
                .ambientTemperature(dto.getAmbientTemperature())
                .humidity(dto.getHumidity())
                .windSpeed(dto.getWindSpeed())
                .windDirection(dto.getWindDirection())
                .airQuality(dto.getAirQuality())
                .uvIndex(dto.getUvIndex())
                .pressure(dto.getPressure())
                .precipitation(dto.getPrecipitation())
                .networkLoad(dto.getNetworkLoad())
                .signalStrength(dto.getSignalStrength())
                .latency(dto.getLatency())
                .packetLoss(dto.getPacketLoss())
                .jitter(dto.getJitter())
                .bandwidth(dto.getBandwidth())
                .throughput(dto.getThroughput())
                .responseTime(dto.getResponseTime())
                .interference(dto.getInterference())
                .cpuUtilization(dto.getCpuUtilization())
                .memoryUsage(dto.getMemoryUsage())
                .diskSpace(dto.getDiskSpace())
                .errorRate(dto.getErrorRate())
                .vibration(dto.getVibration())
                .voltage(dto.getVoltage())
                .build();
    }

    public TelemetryDataDTO toDTO(TelemetryData entity) {
        return TelemetryDataDTO.builder()
                .id(entity.getId())
                .towerId(entity.getTowerId())
                .timestamp(entity.getTimestamp())
                .status(entity.getStatus())
                .battery(entity.getBattery())
                .uptime(entity.getUptime())
                .temperature(entity.getTemperature())
                .ambientTemperature(entity.getAmbientTemperature())
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .windDirection(entity.getWindDirection())
                .airQuality(entity.getAirQuality())
                .uvIndex(entity.getUvIndex())
                .pressure(entity.getPressure())
                .precipitation(entity.getPrecipitation())
                .networkLoad(entity.getNetworkLoad())
                .signalStrength(entity.getSignalStrength())
                .latency(entity.getLatency())
                .packetLoss(entity.getPacketLoss())
                .jitter(entity.getJitter())
                .bandwidth(entity.getBandwidth())
                .throughput(entity.getThroughput())
                .responseTime(entity.getResponseTime())
                .interference(entity.getInterference())
                .cpuUtilization(entity.getCpuUtilization())
                .memoryUsage(entity.getMemoryUsage())
                .diskSpace(entity.getDiskSpace())
                .errorRate(entity.getErrorRate())
                .vibration(entity.getVibration())
                .voltage(entity.getVoltage())
                .build();
    }
}
