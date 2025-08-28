package com.example.demo.dto.mapper;

import com.example.demo.dto.threshold.CreateThresholdRuleDTO;
import com.example.demo.dto.threshold.ThresholdRuleDTO;
import com.example.demo.entities.ThresholdRule;
import com.example.demo.entities.ThresholdMetric;
import com.example.demo.entities.ThresholdCondition;
import com.example.demo.entities.AlertSeverity;
import org.springframework.stereotype.Component;

@Component
public class ThresholdRuleMapper {

    public ThresholdRuleDTO toDTO(ThresholdRule thresholdRule) {
        if (thresholdRule == null) {
            return null;
        }

        return ThresholdRuleDTO.builder()
                .id(thresholdRule.getId())
                .name(thresholdRule.getName())
                .metric(thresholdRule.getMetric() != null ? thresholdRule.getMetric().name().toLowerCase() : null)
                .condition(thresholdRule.getCondition() != null ? thresholdRule.getCondition().name().toLowerCase() : null)
                .value(thresholdRule.getValue())
                .severity(thresholdRule.getSeverity() != null ? thresholdRule.getSeverity().name().toLowerCase() : null)
                .enabled(thresholdRule.getEnabled())
                .description(thresholdRule.getDescription())
                .towerId(thresholdRule.getTowerId())
                .createdAt(thresholdRule.getCreatedAt())
                .updatedAt(thresholdRule.getUpdatedAt())
                .build();
    }

    public ThresholdRule toEntity(CreateThresholdRuleDTO createThresholdRuleDTO) {
        if (createThresholdRuleDTO == null) {
            return null;
        }

        return ThresholdRule.builder()
                .name(createThresholdRuleDTO.getName())
                .metric(parseThresholdMetric(createThresholdRuleDTO.getMetric()))
                .condition(parseThresholdCondition(createThresholdRuleDTO.getCondition()))
                .value(createThresholdRuleDTO.getValue())
                .severity(parseAlertSeverity(createThresholdRuleDTO.getSeverity()))
                .enabled(createThresholdRuleDTO.getEnabled() != null ? createThresholdRuleDTO.getEnabled() : true)
                .description(createThresholdRuleDTO.getDescription())
                .towerId(createThresholdRuleDTO.getTowerId())
                .build();
    }

    private ThresholdMetric parseThresholdMetric(String metric) {
        if (metric == null) {
            return null;
        }
        try {
            return ThresholdMetric.valueOf(metric.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ThresholdMetric.TEMPERATURE;
        }
    }

    private ThresholdCondition parseThresholdCondition(String condition) {
        if (condition == null) {
            return null;
        }
        try {
            return ThresholdCondition.valueOf(condition.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ThresholdCondition.GREATER_THAN;
        }
    }

    private AlertSeverity parseAlertSeverity(String severity) {
        if (severity == null) {
            return AlertSeverity.INFO;
        }
        try {
            return AlertSeverity.valueOf(severity.toUpperCase());
        } catch (IllegalArgumentException e) {
            return AlertSeverity.INFO;
        }
    }
}
