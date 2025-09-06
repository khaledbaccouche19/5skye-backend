package com.example.demo.service;

import com.example.demo.dto.telemetry.CreateTelemetryDataDTO;
import com.example.demo.dto.telemetry.TelemetryDataDTO;
import com.example.demo.dto.mapper.TelemetryDataMapper;
import com.example.demo.entities.TelemetryData;
import com.example.demo.repositories.TelemetryDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelemetryDataService {

    private final TelemetryDataRepository telemetryDataRepository;
    private final TelemetryDataMapper telemetryDataMapper;

    public List<TelemetryDataDTO> getAllTelemetryData() {
        return telemetryDataRepository.findAll().stream()
                .map(telemetryDataMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TelemetryDataDTO getTelemetryDataById(Long id) {
        TelemetryData telemetryData = telemetryDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telemetry data not found with id: " + id));
        return telemetryDataMapper.toDTO(telemetryData);
    }

    public List<TelemetryDataDTO> getTelemetryDataByTowerId(Long towerId) {
        return telemetryDataRepository.findByTowerIdOrderByTimestampDesc(towerId).stream()
                .map(telemetryDataMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TelemetryDataDTO> getTelemetryDataByTowerIdAndTimeRange(Long towerId, Instant startTime, Instant endTime) {
        return telemetryDataRepository.findByTowerIdAndTimestampBetweenOrderByTimestampAsc(towerId, startTime, endTime).stream()
                .map(telemetryDataMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TelemetryDataDTO createTelemetryData(CreateTelemetryDataDTO createTelemetryDataDTO) {
        TelemetryData telemetryData = telemetryDataMapper.toEntity(createTelemetryDataDTO);
        TelemetryData savedTelemetryData = telemetryDataRepository.save(telemetryData);
        return telemetryDataMapper.toDTO(savedTelemetryData);
    }

    public TelemetryDataDTO updateTelemetryData(Long id, CreateTelemetryDataDTO updateTelemetryDataDTO) {
        TelemetryData existingTelemetryData = telemetryDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Telemetry data not found with id: " + id));
        
        // Update all the comprehensive fields
        if (updateTelemetryDataDTO.getTimestamp() != null) {
            existingTelemetryData.setTimestamp(updateTelemetryDataDTO.getTimestamp());
        }
        if (updateTelemetryDataDTO.getStatus() != null) {
            existingTelemetryData.setStatus(updateTelemetryDataDTO.getStatus());
        }
        if (updateTelemetryDataDTO.getBattery() != null) {
            existingTelemetryData.setBattery(updateTelemetryDataDTO.getBattery());
        }
        if (updateTelemetryDataDTO.getUptime() != null) {
            existingTelemetryData.setUptime(updateTelemetryDataDTO.getUptime());
        }
        if (updateTelemetryDataDTO.getTemperature() != null) {
            existingTelemetryData.setTemperature(updateTelemetryDataDTO.getTemperature());
        }
        if (updateTelemetryDataDTO.getAmbientTemperature() != null) {
            existingTelemetryData.setAmbientTemperature(updateTelemetryDataDTO.getAmbientTemperature());
        }
        if (updateTelemetryDataDTO.getHumidity() != null) {
            existingTelemetryData.setHumidity(updateTelemetryDataDTO.getHumidity());
        }
        if (updateTelemetryDataDTO.getWindSpeed() != null) {
            existingTelemetryData.setWindSpeed(updateTelemetryDataDTO.getWindSpeed());
        }
        if (updateTelemetryDataDTO.getWindDirection() != null) {
            existingTelemetryData.setWindDirection(updateTelemetryDataDTO.getWindDirection());
        }
        if (updateTelemetryDataDTO.getAirQuality() != null) {
            existingTelemetryData.setAirQuality(updateTelemetryDataDTO.getAirQuality());
        }
        if (updateTelemetryDataDTO.getUvIndex() != null) {
            existingTelemetryData.setUvIndex(updateTelemetryDataDTO.getUvIndex());
        }
        if (updateTelemetryDataDTO.getPressure() != null) {
            existingTelemetryData.setPressure(updateTelemetryDataDTO.getPressure());
        }
        if (updateTelemetryDataDTO.getPrecipitation() != null) {
            existingTelemetryData.setPrecipitation(updateTelemetryDataDTO.getPrecipitation());
        }
        if (updateTelemetryDataDTO.getNetworkLoad() != null) {
            existingTelemetryData.setNetworkLoad(updateTelemetryDataDTO.getNetworkLoad());
        }
        if (updateTelemetryDataDTO.getSignalStrength() != null) {
            existingTelemetryData.setSignalStrength(updateTelemetryDataDTO.getSignalStrength());
        }
        if (updateTelemetryDataDTO.getLatency() != null) {
            existingTelemetryData.setLatency(updateTelemetryDataDTO.getLatency());
        }
        if (updateTelemetryDataDTO.getPacketLoss() != null) {
            existingTelemetryData.setPacketLoss(updateTelemetryDataDTO.getPacketLoss());
        }
        if (updateTelemetryDataDTO.getJitter() != null) {
            existingTelemetryData.setJitter(updateTelemetryDataDTO.getJitter());
        }
        if (updateTelemetryDataDTO.getBandwidth() != null) {
            existingTelemetryData.setBandwidth(updateTelemetryDataDTO.getBandwidth());
        }
        if (updateTelemetryDataDTO.getThroughput() != null) {
            existingTelemetryData.setThroughput(updateTelemetryDataDTO.getThroughput());
        }
        if (updateTelemetryDataDTO.getResponseTime() != null) {
            existingTelemetryData.setResponseTime(updateTelemetryDataDTO.getResponseTime());
        }
        if (updateTelemetryDataDTO.getInterference() != null) {
            existingTelemetryData.setInterference(updateTelemetryDataDTO.getInterference());
        }
        if (updateTelemetryDataDTO.getCpuUtilization() != null) {
            existingTelemetryData.setCpuUtilization(updateTelemetryDataDTO.getCpuUtilization());
        }
        if (updateTelemetryDataDTO.getMemoryUsage() != null) {
            existingTelemetryData.setMemoryUsage(updateTelemetryDataDTO.getMemoryUsage());
        }
        if (updateTelemetryDataDTO.getDiskSpace() != null) {
            existingTelemetryData.setDiskSpace(updateTelemetryDataDTO.getDiskSpace());
        }
        if (updateTelemetryDataDTO.getErrorRate() != null) {
            existingTelemetryData.setErrorRate(updateTelemetryDataDTO.getErrorRate());
        }
        if (updateTelemetryDataDTO.getVibration() != null) {
            existingTelemetryData.setVibration(updateTelemetryDataDTO.getVibration());
        }
        if (updateTelemetryDataDTO.getVoltage() != null) {
            existingTelemetryData.setVoltage(updateTelemetryDataDTO.getVoltage());
        }
        
        TelemetryData updatedTelemetryData = telemetryDataRepository.save(existingTelemetryData);
        return telemetryDataMapper.toDTO(updatedTelemetryData);
    }

    public void deleteTelemetryData(Long id) {
        if (!telemetryDataRepository.existsById(id)) {
            throw new RuntimeException("Telemetry data not found with id: " + id);
        }
        telemetryDataRepository.deleteById(id);
    }

    public void deleteTelemetryDataByTowerId(Long towerId) {
        telemetryDataRepository.deleteByTowerId(towerId);
    }

    // New methods for comprehensive telemetry data
    public TelemetryDataDTO getLatestTelemetryDataByTowerId(Long towerId) {
        return telemetryDataRepository.findTopByTowerIdOrderByTimestampDesc(towerId)
                .map(telemetryDataMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("No telemetry data found for tower: " + towerId));
    }

    public List<TelemetryDataDTO> getTelemetryDataByTowerIdAndStatus(Long towerId, String status) {
        return telemetryDataRepository.findByTowerIdAndStatusOrderByTimestampDesc(towerId, status).stream()
                .map(telemetryDataMapper::toDTO)
                .collect(Collectors.toList());
    }
}
