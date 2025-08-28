package com.example.demo.service;

import com.example.demo.dto.hardware.CreateHardwareDTO;
import com.example.demo.dto.hardware.HardwareDTO;
import com.example.demo.dto.hardware.HardwareSummaryDTO;
import com.example.demo.dto.mapper.HardwareMapper;
import com.example.demo.entities.Hardware;
import com.example.demo.entities.Tower;
import com.example.demo.repositories.HardwareRepository;
import com.example.demo.repositories.TowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HardwareService {

    private final HardwareRepository hardwareRepository;
    private final TowerRepository towerRepository;
    private final HardwareMapper hardwareMapper;

    public List<HardwareDTO> getAllHardware() {
        return hardwareRepository.findAll().stream()
                .map(hardwareMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HardwareDTO getHardwareById(Long id) {
        Hardware hardware = hardwareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hardware not found with id: " + id));
        return hardwareMapper.toDTO(hardware);
    }

    public List<HardwareDTO> getHardwareByTowerId(Long towerId) {
        return hardwareRepository.findByTowerId(towerId).stream()
                .map(hardwareMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HardwareSummaryDTO> getAllHardwareSummaries() {
        return hardwareRepository.findAll().stream()
                .map(hardwareMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public HardwareDTO createHardware(CreateHardwareDTO createHardwareDTO) {
        Hardware hardware = hardwareMapper.toEntity(createHardwareDTO);
        
        // Set the tower relationship if towerId is provided
        if (createHardwareDTO.getTowerId() != null) {
            Tower tower = towerRepository.findById(createHardwareDTO.getTowerId())
                    .orElseThrow(() -> new RuntimeException("Tower not found with id: " + createHardwareDTO.getTowerId()));
            hardware.setTower(tower);
        }
        
        Hardware savedHardware = hardwareRepository.save(hardware);
        return hardwareMapper.toDTO(savedHardware);
    }

    public HardwareDTO updateHardware(Long id, CreateHardwareDTO updateHardwareDTO) {
        Hardware existingHardware = hardwareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hardware not found with id: " + id));
        
        existingHardware.setName(updateHardwareDTO.getName());
        existingHardware.setVendor(updateHardwareDTO.getVendor());
        existingHardware.setModel(updateHardwareDTO.getModel());
        existingHardware.setSerialNumber(updateHardwareDTO.getSerialNumber());
        existingHardware.setSpecifications(updateHardwareDTO.getSpecifications());
        
        // Update the tower relationship if towerId is provided
        if (updateHardwareDTO.getTowerId() != null) {
            Tower tower = towerRepository.findById(updateHardwareDTO.getTowerId())
                    .orElseThrow(() -> new RuntimeException("Tower not found with id: " + updateHardwareDTO.getTowerId()));
            existingHardware.setTower(tower);
        }
        
        Hardware updatedHardware = hardwareRepository.save(existingHardware);
        return hardwareMapper.toDTO(updatedHardware);
    }

    public void deleteHardware(Long id) {
        if (!hardwareRepository.existsById(id)) {
            throw new RuntimeException("Hardware not found with id: " + id);
        }
        hardwareRepository.deleteById(id);
    }

    public void deleteHardwareByTowerId(Long towerId) {
        hardwareRepository.deleteByTowerId(towerId);
    }
}
