package com.example.demo.service;

import com.example.demo.entities.NetworkMetrics;
import com.example.demo.repositories.NetworkMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkMetricsService {

    private final NetworkMetricsRepository repository;

    @Autowired
    public NetworkMetricsService(NetworkMetricsRepository repository) {
        this.repository = repository;
    }

    public NetworkMetrics save(NetworkMetrics metric) {
        return repository.save(metric);
    }

    public List<NetworkMetrics> findAll() {
        return repository.findAll();
    }

    public NetworkMetrics findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public NetworkMetrics update(Long id, NetworkMetrics updated) {
        return repository.findById(id).map(existing -> {
            existing.setTimestamp(updated.getTimestamp());
            existing.setSignalStrength(updated.getSignalStrength());
            existing.setThroughput(updated.getThroughput());
            existing.setLatency(updated.getLatency());
            existing.setPacketLoss(updated.getPacketLoss());
            existing.setJitter(updated.getJitter());
            existing.setErrorRate(updated.getErrorRate());
            existing.setBandwidth(updated.getBandwidth());
            existing.setCpuUsage(updated.getCpuUsage());
            existing.setMemoryUsage(updated.getMemoryUsage());
            existing.setTemperature(updated.getTemperature());
            existing.setInterfaceStatus(updated.getInterfaceStatus());
            existing.setLinkStatus(updated.getLinkStatus());
            existing.setNetworkLoad(updated.getNetworkLoad());
            existing.setTrafficVolume(updated.getTrafficVolume());
            existing.setActiveConnections(updated.getActiveConnections());
            existing.setSessionCount(updated.getSessionCount());
            existing.setQueueDepth(updated.getQueueDepth());
            existing.setBufferUtilization(updated.getBufferUtilization());
            existing.setErrorCount(updated.getErrorCount());
            existing.setRetransmissionRate(updated.getRetransmissionRate());
            existing.setCongestionLevel(updated.getCongestionLevel());
            existing.setAvailabilityPercentage(updated.getAvailabilityPercentage());
            existing.setPerformanceIndex(updated.getPerformanceIndex());
            existing.setServiceLevel(updated.getServiceLevel());
            existing.setAlarmStatus(updated.getAlarmStatus());
            existing.setEquipment(updated.getEquipment());
            return repository.save(existing);
        }).orElse(null);
    }
}
