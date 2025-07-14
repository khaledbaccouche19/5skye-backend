package com.example.demo.service;

import com.example.demo.entities.EquipmentMetrics;
import com.example.demo.repositories.EquipmentMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentMetricsService {

    private final EquipmentMetricsRepository repository;

    @Autowired
    public EquipmentMetricsService(EquipmentMetricsRepository repository) {
        this.repository = repository;
    }

    public EquipmentMetrics save(EquipmentMetrics metrics) {
        return repository.save(metrics);
    }

    public List<EquipmentMetrics> findAll() {
        return repository.findAll();
    }

    public EquipmentMetrics findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public EquipmentMetrics update(Long id, EquipmentMetrics updated) {
        return repository.findById(id).map(existing -> {
            existing.setTimestamp(updated.getTimestamp());
            existing.setMetricType(updated.getMetricType());
            existing.setValue(updated.getValue());
            existing.setUnit(updated.getUnit());
            existing.setStatus(updated.getStatus());
            existing.setDescription(updated.getDescription());
            existing.setCategory(updated.getCategory());
            existing.setSource(updated.getSource());
            existing.setMethod(updated.getMethod());
            existing.setAccuracy(updated.getAccuracy());
            existing.setPrecision(updated.getPrecision());
            existing.setUncertainty(updated.getUncertainty());
            existing.setCalibrationStatus(updated.getCalibrationStatus());
            existing.setValidationStatus(updated.getValidationStatus());
            existing.setDataQuality(updated.getDataQuality());
            existing.setProcessingAlgorithm(updated.getProcessingAlgorithm());
            existing.setAggregationMethod(updated.getAggregationMethod());
            existing.setSamplingRate(updated.getSamplingRate());
            existing.setFilterApplied(updated.getFilterApplied());
            existing.setNoiseLevel(updated.getNoiseLevel());
            existing.setSignalStrength(updated.getSignalStrength());
            existing.setCompensationApplied(updated.getCompensationApplied());
            existing.setReferenceStandard(updated.getReferenceStandard());
            existing.setTraceability(updated.getTraceability());
            existing.setConfidenceLevel(updated.getConfidenceLevel());
            existing.setStatisticalAnalysis(updated.getStatisticalAnalysis());
            existing.setTrendAnalysis(updated.getTrendAnalysis());
            existing.setAnomalyDetection(updated.getAnomalyDetection());
            existing.setCorrelationAnalysis(updated.getCorrelationAnalysis());
            existing.setPredictiveAnalysis(updated.getPredictiveAnalysis());
            existing.setThresholdComparison(updated.getThresholdComparison());
            existing.setAlarmStatus(updated.getAlarmStatus());
            existing.setAlertLevel(updated.getAlertLevel());
            existing.setActionRequired(updated.getActionRequired());
            existing.setActionTaken(updated.getActionTaken());
            existing.setResponseTime(updated.getResponseTime());
            existing.setEscalationLevel(updated.getEscalationLevel());
            existing.setAssignedTo(updated.getAssignedTo());
            existing.setResolutionStatus(updated.getResolutionStatus());
            existing.setResolutionTime(updated.getResolutionTime());
            existing.setFollowUpRequired(updated.getFollowUpRequired());
            existing.setReportGenerated(updated.getReportGenerated());
            existing.setDocumentationUpdated(updated.getDocumentationUpdated());
            existing.setAuditTrail(updated.getAuditTrail());
            existing.setComplianceCheck(updated.getComplianceCheck());
            existing.setRegulatoryRequirement(updated.getRegulatoryRequirement());
            existing.setPerformanceIndicator(updated.getPerformanceIndicator());
            existing.setBenchmarkComparison(updated.getBenchmarkComparison());
            existing.setImprovementOpportunity(updated.getImprovementOpportunity());
            existing.setRecommendedAction(updated.getRecommendedAction());
            existing.setCostImplication(updated.getCostImplication());
            existing.setBusinessImpact(updated.getBusinessImpact());
            existing.setRiskAssessment(updated.getRiskAssessment());
            existing.setMitigationStrategy(updated.getMitigationStrategy());
            existing.setContingencyPlan(updated.getContingencyPlan());
            existing.setLessonLearned(updated.getLessonLearned());
            existing.setBestPractice(updated.getBestPractice());
            existing.setKnowledgeBase(updated.getKnowledgeBase());
            existing.setDecisionSupport(updated.getDecisionSupport());
            existing.setAutomationLevel(updated.getAutomationLevel());
            existing.setHumanIntervention(updated.getHumanIntervention());
            existing.setEquipment(updated.getEquipment());
            return repository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
