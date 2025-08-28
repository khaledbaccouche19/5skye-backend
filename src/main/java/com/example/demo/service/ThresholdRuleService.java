package com.example.demo.service;

import com.example.demo.dto.threshold.CreateThresholdRuleDTO;
import com.example.demo.dto.threshold.ThresholdRuleDTO;
import com.example.demo.dto.mapper.ThresholdRuleMapper;
import com.example.demo.entities.ThresholdRule;
import com.example.demo.repositories.ThresholdRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThresholdRuleService {

    private final ThresholdRuleRepository thresholdRuleRepository;
    private final ThresholdRuleMapper thresholdRuleMapper;

    public List<ThresholdRuleDTO> getAllThresholdRules() {
        return thresholdRuleRepository.findAll().stream()
                .map(thresholdRuleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ThresholdRuleDTO getThresholdRuleById(Long id) {
        ThresholdRule thresholdRule = thresholdRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Threshold rule not found with id: " + id));
        return thresholdRuleMapper.toDTO(thresholdRule);
    }

    public List<ThresholdRuleDTO> getThresholdRulesByTowerId(Long towerId) {
        return thresholdRuleRepository.findByTowerId(towerId).stream()
                .map(thresholdRuleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ThresholdRuleDTO createThresholdRule(CreateThresholdRuleDTO createThresholdRuleDTO) {
        ThresholdRule thresholdRule = thresholdRuleMapper.toEntity(createThresholdRuleDTO);
        ThresholdRule savedThresholdRule = thresholdRuleRepository.save(thresholdRule);
        return thresholdRuleMapper.toDTO(savedThresholdRule);
    }

    public ThresholdRuleDTO updateThresholdRule(Long id, CreateThresholdRuleDTO updateThresholdRuleDTO) {
        ThresholdRule existingThresholdRule = thresholdRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Threshold rule not found with id: " + id));
        
        existingThresholdRule.setName(updateThresholdRuleDTO.getName());
        existingThresholdRule.setDescription(updateThresholdRuleDTO.getDescription());
        existingThresholdRule.setTowerId(updateThresholdRuleDTO.getTowerId());
        
        ThresholdRule updatedThresholdRule = thresholdRuleRepository.save(existingThresholdRule);
        return thresholdRuleMapper.toDTO(updatedThresholdRule);
    }

    public void deleteThresholdRule(Long id) {
        if (!thresholdRuleRepository.existsById(id)) {
            throw new RuntimeException("Threshold rule not found with id: " + id);
        }
        thresholdRuleRepository.deleteById(id);
    }

    public void deleteThresholdRulesByTowerId(Long towerId) {
        thresholdRuleRepository.deleteByTowerId(towerId);
    }
}
