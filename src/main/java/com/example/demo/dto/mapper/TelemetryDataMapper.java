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
                .humidity(dto.getHumidity())
                .windSpeed(dto.getWindSpeed())
                .airQuality(dto.getAirQuality())
                .uvIndex(dto.getUvIndex())
                .pressure(dto.getPressure())
                .networkLoad(dto.getNetworkLoad())
                .signalStrength(dto.getSignalStrength())
                .latency(dto.getLatency())
                .packetLoss(dto.getPacketLoss())
                .jitter(dto.getJitter())
                .bandwidth(dto.getBandwidth())
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
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .airQuality(entity.getAirQuality())
                .uvIndex(entity.getUvIndex())
                .pressure(entity.getPressure())
                .networkLoad(entity.getNetworkLoad())
                .signalStrength(entity.getSignalStrength())
                .latency(entity.getLatency())
                .packetLoss(entity.getPacketLoss())
                .jitter(entity.getJitter())
                .bandwidth(entity.getBandwidth())
                .voltage(entity.getVoltage())
                .build();
    }
}
