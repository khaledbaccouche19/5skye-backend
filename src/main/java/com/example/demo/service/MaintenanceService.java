package com.example.demo.service;

import com.example.demo.dto.maintenance.CreateMaintenanceDTO;
import com.example.demo.dto.maintenance.MaintenanceDTO;
import com.example.demo.dto.maintenance.UpdateMaintenanceStatusDTO;
import com.example.demo.entities.Maintenance;
import com.example.demo.entities.MaintenanceStatus;
import com.example.demo.entities.Tower;
import com.example.demo.repository.MaintenanceRepository;
import com.example.demo.repositories.TowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final TowerRepository towerRepository;

    public List<MaintenanceDTO> getAllMaintenance() {
        return maintenanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MaintenanceDTO getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
        return convertToDTO(maintenance);
    }

    public List<MaintenanceDTO> getMaintenanceByTowerId(Long towerId) {
        return maintenanceRepository.findByTowerIdOrderByStartDateDesc(towerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getMaintenanceByStatus(MaintenanceStatus status) {
        return maintenanceRepository.findByStatusOrderByStartDateAsc(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getMaintenanceByTowerIdAndStatus(Long towerId, MaintenanceStatus status) {
        return maintenanceRepository.findByTowerIdAndStatusOrderByStartDateDesc(towerId, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getOverdueMaintenance() {
        return maintenanceRepository.findOverdueMaintenance(MaintenanceStatus.SCHEDULED, LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenanceDTO> getUpcomingRecurringMaintenance() {
        return maintenanceRepository.findUpcomingRecurringMaintenance(LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MaintenanceDTO createMaintenance(CreateMaintenanceDTO createMaintenanceDTO) {
        Tower tower = towerRepository.findById(createMaintenanceDTO.getTowerId())
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + createMaintenanceDTO.getTowerId()));

        Maintenance maintenance = Maintenance.builder()
                .title(createMaintenanceDTO.getTitle())
                .description(createMaintenanceDTO.getDescription())
                .type(createMaintenanceDTO.getType())
                .priority(createMaintenanceDTO.getPriority())
                .status(createMaintenanceDTO.getStatus())
                .startDate(createMaintenanceDTO.getStartDate())
                .endDate(createMaintenanceDTO.getEndDate())
                .scheduledDate(createMaintenanceDTO.getScheduledDate())
                .technician(createMaintenanceDTO.getTechnician())
                .technicianContact(createMaintenanceDTO.getTechnicianContact())
                .estimatedDurationHours(createMaintenanceDTO.getEstimatedDurationHours())
                .actualDurationHours(createMaintenanceDTO.getActualDurationHours())
                .estimatedCost(createMaintenanceDTO.getEstimatedCost())
                .actualCost(createMaintenanceDTO.getActualCost())
                .notes(createMaintenanceDTO.getNotes())
                .partsUsed(createMaintenanceDTO.getPartsUsed())
                .isRecurring(createMaintenanceDTO.getIsRecurring())
                .recurrenceIntervalDays(createMaintenanceDTO.getRecurrenceIntervalDays())
                .nextMaintenanceDate(createMaintenanceDTO.getNextMaintenanceDate())
                .tower(tower)
                .build();

        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);
        return convertToDTO(savedMaintenance);
    }

    public MaintenanceDTO updateMaintenance(Long id, CreateMaintenanceDTO updateMaintenanceDTO) {
        Maintenance existingMaintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));

        Tower tower = towerRepository.findById(updateMaintenanceDTO.getTowerId())
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + updateMaintenanceDTO.getTowerId()));

        existingMaintenance.setTitle(updateMaintenanceDTO.getTitle());
        existingMaintenance.setDescription(updateMaintenanceDTO.getDescription());
        existingMaintenance.setType(updateMaintenanceDTO.getType());
        existingMaintenance.setPriority(updateMaintenanceDTO.getPriority());
        existingMaintenance.setStatus(updateMaintenanceDTO.getStatus());
        existingMaintenance.setStartDate(updateMaintenanceDTO.getStartDate());
        existingMaintenance.setEndDate(updateMaintenanceDTO.getEndDate());
        existingMaintenance.setScheduledDate(updateMaintenanceDTO.getScheduledDate());
        existingMaintenance.setTechnician(updateMaintenanceDTO.getTechnician());
        existingMaintenance.setTechnicianContact(updateMaintenanceDTO.getTechnicianContact());
        existingMaintenance.setEstimatedDurationHours(updateMaintenanceDTO.getEstimatedDurationHours());
        existingMaintenance.setActualDurationHours(updateMaintenanceDTO.getActualDurationHours());
        existingMaintenance.setEstimatedCost(updateMaintenanceDTO.getEstimatedCost());
        existingMaintenance.setActualCost(updateMaintenanceDTO.getActualCost());
        existingMaintenance.setNotes(updateMaintenanceDTO.getNotes());
        existingMaintenance.setPartsUsed(updateMaintenanceDTO.getPartsUsed());
        existingMaintenance.setIsRecurring(updateMaintenanceDTO.getIsRecurring());
        existingMaintenance.setRecurrenceIntervalDays(updateMaintenanceDTO.getRecurrenceIntervalDays());
        existingMaintenance.setNextMaintenanceDate(updateMaintenanceDTO.getNextMaintenanceDate());
        existingMaintenance.setTower(tower);

        Maintenance updatedMaintenance = maintenanceRepository.save(existingMaintenance);
        return convertToDTO(updatedMaintenance);
    }

    public void deleteMaintenance(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new RuntimeException("Maintenance not found with id: " + id);
        }
        maintenanceRepository.deleteById(id);
    }

    public Long getMaintenanceCountByTowerIdAndStatus(Long towerId, MaintenanceStatus status) {
        return maintenanceRepository.countByTowerIdAndStatus(towerId, status);
    }

    private MaintenanceDTO convertToDTO(Maintenance maintenance) {
        return MaintenanceDTO.builder()
                .id(maintenance.getId())
                .title(maintenance.getTitle())
                .description(maintenance.getDescription())
                .type(maintenance.getType())
                .priority(maintenance.getPriority())
                .status(maintenance.getStatus())
                .startDate(maintenance.getStartDate())
                .endDate(maintenance.getEndDate())
                .scheduledDate(maintenance.getScheduledDate())
                .technician(maintenance.getTechnician())
                .technicianContact(maintenance.getTechnicianContact())
                .estimatedDurationHours(maintenance.getEstimatedDurationHours())
                .actualDurationHours(maintenance.getActualDurationHours())
                .estimatedCost(maintenance.getEstimatedCost())
                .actualCost(maintenance.getActualCost())
                .notes(maintenance.getNotes())
                .partsUsed(maintenance.getPartsUsed())
                .isRecurring(maintenance.getIsRecurring())
                .recurrenceIntervalDays(maintenance.getRecurrenceIntervalDays())
                .nextMaintenanceDate(maintenance.getNextMaintenanceDate())
                .createdAt(maintenance.getCreatedAt())
                .updatedAt(maintenance.getUpdatedAt())
                .towerId(maintenance.getTower().getId())
                .towerName(maintenance.getTower().getName())
                .build();
    }
    
    // ========== WORKFLOW METHODS ==========
    
    /**
     * Start maintenance work - change status to IN_PROGRESS
     */
    public Maintenance startMaintenance(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
        
        if (maintenance.getStatus() != MaintenanceStatus.PLANNED && 
            maintenance.getStatus() != MaintenanceStatus.SCHEDULED) {
            throw new RuntimeException("Cannot start maintenance with status: " + maintenance.getStatus());
        }
        
        maintenance.setStatus(MaintenanceStatus.IN_PROGRESS);
        return maintenanceRepository.save(maintenance);
    }
    
    /**
     * Update maintenance status and progress
     */
    public Maintenance updateMaintenanceStatus(Long id, UpdateMaintenanceStatusDTO updateDTO) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found with id: " + id));
        
        // Update status
        if (updateDTO.getStatus() != null) {
            maintenance.setStatus(updateDTO.getStatus());
        }
        
        // Update end date
        if (updateDTO.getEndDate() != null) {
            maintenance.setEndDate(updateDTO.getEndDate());
        }
        
        // Update actual duration
        if (updateDTO.getActualDurationHours() != null) {
            maintenance.setActualDurationHours(updateDTO.getActualDurationHours());
        }
        
        // Update actual cost
        if (updateDTO.getActualCost() != null) {
            maintenance.setActualCost(updateDTO.getActualCost());
        }
        
        // Update notes
        if (updateDTO.getCompletionNotes() != null) {
            String existingNotes = maintenance.getNotes() != null ? maintenance.getNotes() : "";
            maintenance.setNotes(existingNotes + "\n\nCompletion Notes: " + updateDTO.getCompletionNotes());
        }
        
        if (updateDTO.getTechnicianNotes() != null) {
            String existingNotes = maintenance.getNotes() != null ? maintenance.getNotes() : "";
            maintenance.setNotes(existingNotes + "\n\nTechnician Notes: " + updateDTO.getTechnicianNotes());
        }
        
        if (updateDTO.getQualityCheckNotes() != null) {
            String existingNotes = maintenance.getNotes() != null ? maintenance.getNotes() : "";
            maintenance.setNotes(existingNotes + "\n\nQuality Check: " + updateDTO.getQualityCheckNotes());
        }
        
        // Update next maintenance recommendation
        if (updateDTO.getNextMaintenanceRecommendation() != null) {
            String existingNotes = maintenance.getNotes() != null ? maintenance.getNotes() : "";
            maintenance.setNotes(existingNotes + "\n\nNext Maintenance: " + updateDTO.getNextMaintenanceRecommendation());
        }
        
        return maintenanceRepository.save(maintenance);
    }
    
    /**
     * Get active maintenance (IN_PROGRESS, ON_HOLD)
     */
    public List<Maintenance> getActiveMaintenance() {
        return maintenanceRepository.findByStatusInOrderByStartDateAsc(
                List.of(MaintenanceStatus.IN_PROGRESS, MaintenanceStatus.ON_HOLD)
        );
    }
    
    public void deleteMaintenanceByTowerId(Long towerId) {
        maintenanceRepository.deleteByTowerId(towerId);
    }
}
