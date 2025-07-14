package com.example.demo.service;

import com.example.demo.entities.SensorReading;
import com.example.demo.repositories.SensorReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorReadingService {

    private final SensorReadingRepository repository;

    @Autowired
    public SensorReadingService(SensorReadingRepository repository) {
        this.repository = repository;
    }

    public SensorReading save(SensorReading reading) {
        return repository.save(reading);
    }

    public List<SensorReading> findAll() {
        return repository.findAll();
    }

    public SensorReading findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SensorReading update(Long id, SensorReading updated) {
        return repository.findById(id).map(existing -> {
            existing.setValue(updated.getValue());
            existing.setTimestamp(updated.getTimestamp());
            existing.setUnit(updated.getUnit());
            existing.setIsAlarm(updated.getIsAlarm());
            existing.setReadingType(updated.getReadingType());
            existing.setDataQuality(updated.getDataQuality());
            existing.setCalibrationStatus(updated.getCalibrationStatus());
            existing.setMeasurementMethod(updated.getMeasurementMethod());
            existing.setEnvironmentalConditions(updated.getEnvironmentalConditions());
            existing.setOperatorNotes(updated.getOperatorNotes());
            existing.setValidationStatus(updated.getValidationStatus());
            existing.setDataSource(updated.getDataSource());
            existing.setProcessingAlgorithm(updated.getProcessingAlgorithm());
            existing.setAggregationMethod(updated.getAggregationMethod());
            existing.setSamplingRate(updated.getSamplingRate());
            existing.setFilterApplied(updated.getFilterApplied());
            existing.setNoiseLevel(updated.getNoiseLevel());
            existing.setSignalStrength(updated.getSignalStrength());
            existing.setCompensationApplied(updated.getCompensationApplied());
            existing.setReferenceStandard(updated.getReferenceStandard());
            existing.setTraceability(updated.getTraceability());
            existing.setUncertainty(updated.getUncertainty());
            existing.setConfidenceLevel(updated.getConfidenceLevel());
            existing.setStatisticalAnalysis(updated.getStatisticalAnalysis());
            existing.setTrendAnalysis(updated.getTrendAnalysis());
            existing.setAnomalyDetection(updated.getAnomalyDetection());
            existing.setCorrelationAnalysis(updated.getCorrelationAnalysis());
            existing.setPredictiveAnalysis(updated.getPredictiveAnalysis());
            existing.setReportGenerated(updated.getReportGenerated());
            existing.setActionTaken(updated.getActionTaken());
            existing.setFollowUpRequired(updated.getFollowUpRequired());
            existing.setSensor(updated.getSensor());
            return repository.save(existing);
        }).orElse(null);
    }
}
