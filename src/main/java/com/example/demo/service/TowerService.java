package com.example.demo.service;

import com.example.demo.dto.tower.CreateTowerDTO;
import com.example.demo.dto.tower.TowerDTO;
import com.example.demo.dto.tower.TowerSummaryDTO;
import com.example.demo.dto.mapper.TowerMapper;
import com.example.demo.entities.Tower;
import com.example.demo.entities.TowerStatus;
import com.example.demo.repositories.TowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TowerService {

    private final TowerRepository towerRepository;
    private final TowerMapper towerMapper;

    public List<TowerDTO> getAllTowers() {
        return towerRepository.findAll().stream()
                .map(towerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TowerDTO getTowerById(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        return towerMapper.toDTO(tower);
    }

    public List<TowerSummaryDTO> getAllTowerSummaries() {
        return towerRepository.findAll().stream()
                .map(towerMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public TowerDTO createTower(CreateTowerDTO createTowerDTO) {
        // Validate the DTO before creating the entity
        validateTowerData(createTowerDTO);
        
        Tower tower = towerMapper.toEntity(createTowerDTO);
        Tower savedTower = towerRepository.save(tower);
        return towerMapper.toDTO(savedTower);
    }

    public TowerDTO updateTower(Long id, CreateTowerDTO updateTowerDTO) {
        Tower existingTower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        // Validate the update data
        validateTowerUpdate(updateTowerDTO, existingTower);
        
        // Only update fields that are provided
        if (updateTowerDTO.getName() != null) {
            existingTower.setName(updateTowerDTO.getName());
        }
        if (updateTowerDTO.getStatus() != null) {
            existingTower.setStatus(parseTowerStatus(updateTowerDTO.getStatus()));
        }
        if (updateTowerDTO.getLatitude() != null) {
            existingTower.setLatitude(updateTowerDTO.getLatitude());
        }
        if (updateTowerDTO.getLongitude() != null) {
            existingTower.setLongitude(updateTowerDTO.getLongitude());
        }
        if (updateTowerDTO.getCity() != null) {
            existingTower.setCity(updateTowerDTO.getCity());
        }
        if (updateTowerDTO.getBattery() != null) {
            existingTower.setBattery(updateTowerDTO.getBattery());
        }
        if (updateTowerDTO.getTemperature() != null) {
            existingTower.setTemperature(updateTowerDTO.getTemperature());
        }
        if (updateTowerDTO.getUptime() != null) {
            existingTower.setUptime(updateTowerDTO.getUptime());
        }
        if (updateTowerDTO.getNetworkLoad() != null) {
            existingTower.setNetworkLoad(updateTowerDTO.getNetworkLoad());
        }
        if (updateTowerDTO.getUseCase() != null) {
            existingTower.setUseCase(updateTowerDTO.getUseCase());
        }
        if (updateTowerDTO.getRegion() != null) {
            existingTower.setRegion(updateTowerDTO.getRegion());
        }
        if (updateTowerDTO.getLastMaintenance() != null) {
            existingTower.setLastMaintenance(java.time.LocalDate.parse(updateTowerDTO.getLastMaintenance()));
        }
        if (updateTowerDTO.getModel3dPath() != null) {
            existingTower.setModel3dPath(updateTowerDTO.getModel3dPath());
        }
        
        // Validate the final entity before saving
        validateTowerEntity(existingTower);
        
        Tower updatedTower = towerRepository.save(existingTower);
        return towerMapper.toDTO(updatedTower);
    }

    private TowerStatus parseTowerStatus(String status) {
        if (status == null) {
            return null;
        }
        try {
            return TowerStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return TowerStatus.OFFLINE;
        }
    }

    public void deleteTower(Long id) {
        // Load tower with all relationships using separate queries
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        // Load relationships separately to avoid MultipleBagFetchException
        Tower towerWithHardware = towerRepository.findByIdWithHardware(id).orElse(tower);
        Tower towerWithAlerts = towerRepository.findByIdWithAlerts(id).orElse(tower);
        Tower towerWithThresholdRules = towerRepository.findByIdWithThresholdRules(id).orElse(tower);
        Tower towerWithTelemetryData = towerRepository.findByIdWithTelemetryData(id).orElse(tower);
        
        // Combine all relationships
        tower.setHardware(towerWithHardware.getHardware());
        tower.setAlerts(towerWithAlerts.getAlerts());
        tower.setThresholdRules(towerWithThresholdRules.getThresholdRules());
        tower.setTelemetryData(towerWithTelemetryData.getTelemetryData());
        
        // Clear all relationships to ensure proper cascade deletion
        tower.getHardware().clear();
        tower.getAlerts().clear();
        tower.getThresholdRules().clear();
        tower.getTelemetryData().clear();
        
        // Clean up 3D model file if it exists
        cleanupModelFile(tower.getModel3dPath());
        
        towerRepository.delete(tower);
    }

    public boolean canDeleteTower(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        // Check each relationship separately
        Tower towerWithHardware = towerRepository.findByIdWithHardware(id).orElse(tower);
        Tower towerWithAlerts = towerRepository.findByIdWithAlerts(id).orElse(tower);
        Tower towerWithThresholdRules = towerRepository.findByIdWithThresholdRules(id).orElse(tower);
        Tower towerWithTelemetryData = towerRepository.findByIdWithTelemetryData(id).orElse(tower);
        
        return towerWithHardware.getHardware().isEmpty() && 
               towerWithAlerts.getAlerts().isEmpty() && 
               towerWithThresholdRules.getThresholdRules().isEmpty() && 
               towerWithTelemetryData.getTelemetryData().isEmpty();
    }

    public String getTowerDependenciesInfo(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        // Load each relationship separately
        Tower towerWithHardware = towerRepository.findByIdWithHardware(id).orElse(tower);
        Tower towerWithAlerts = towerRepository.findByIdWithAlerts(id).orElse(tower);
        Tower towerWithThresholdRules = towerRepository.findByIdWithThresholdRules(id).orElse(tower);
        Tower towerWithTelemetryData = towerRepository.findByIdWithTelemetryData(id).orElse(tower);
        
        StringBuilder info = new StringBuilder();
        if (!towerWithHardware.getHardware().isEmpty()) {
            info.append("Hardware components: ").append(towerWithHardware.getHardware().size()).append(", ");
        }
        if (!towerWithAlerts.getAlerts().isEmpty()) {
            info.append("Active alerts: ").append(towerWithAlerts.getAlerts().size()).append(", ");
        }
        if (!towerWithThresholdRules.getThresholdRules().isEmpty()) {
            info.append("Threshold rules: ").append(towerWithThresholdRules.getThresholdRules().size()).append(", ");
        }
        if (!towerWithTelemetryData.getTelemetryData().isEmpty()) {
            info.append("Telemetry data records: ").append(towerWithTelemetryData.getTelemetryData().size()).append(", ");
        }
        
        if (info.length() > 0) {
            info.setLength(info.length() - 2); // Remove last ", "
            return info.toString();
        }
        return "No dependencies";
    }

    public TowerDTO softDeleteTower(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        // Mark tower as deactivated instead of deleting
        tower.setStatus(TowerStatus.DEACTIVATED);
        
        Tower savedTower = towerRepository.save(tower);
        return towerMapper.toDTO(savedTower);
    }

    public TowerDTO reactivateTower(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        if (tower.getStatus() != TowerStatus.DEACTIVATED) {
            throw new RuntimeException("Tower is not deactivated. Current status: " + tower.getStatus());
        }
        
        // Reactivate tower
        tower.setStatus(TowerStatus.ONLINE);
        
        Tower savedTower = towerRepository.save(tower);
        return towerMapper.toDTO(savedTower);
    }

    public TowerDTO updateTower3DModel(Long id, String model3dPath) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        tower.setModel3dPath(model3dPath);
        
        Tower savedTower = towerRepository.save(tower);
        return towerMapper.toDTO(savedTower);
    }

    public String getTower3DModel(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        return tower.getModel3dPath();
    }

    public void cleanupModelFile(String modelPath) {
        if (modelPath != null && !modelPath.trim().isEmpty()) {
            try {
                // Remove leading slash if present
                if (modelPath.startsWith("/")) {
                    modelPath = modelPath.substring(1);
                }
                
                java.nio.file.Path filePath = java.nio.file.Paths.get("uploads/models", modelPath);
                if (java.nio.file.Files.exists(filePath)) {
                    java.nio.file.Files.delete(filePath);
                }
            } catch (Exception e) {
                // Log error but don't fail the operation
                System.err.println("Failed to cleanup model file: " + e.getMessage());
            }
        }
    }

    // Validation methods
    private void validateTowerData(CreateTowerDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tower name is required");
        }
        if (dto.getName().trim().length() < 2 || dto.getName().trim().length() > 100) {
            throw new IllegalArgumentException("Tower name must be between 2 and 100 characters");
        }
        
        if (dto.getCity() == null || dto.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (dto.getCity().trim().length() < 2 || dto.getCity().trim().length() > 100) {
            throw new IllegalArgumentException("City name must be between 2 and 100 characters");
        }
        
        validateCoordinates(dto.getLatitude(), dto.getLongitude());
        validateNumericFields(dto);
    }
    
    private void validateTowerUpdate(CreateTowerDTO dto, Tower existingTower) {
        // Validate name if provided
        if (dto.getName() != null) {
            if (dto.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tower name cannot be empty");
            }
            if (dto.getName().trim().length() < 2 || dto.getName().trim().length() > 100) {
                throw new IllegalArgumentException("Tower name must be between 2 and 100 characters");
            }
        }
        
        // Validate city if provided
        if (dto.getCity() != null) {
            if (dto.getCity().trim().isEmpty()) {
                throw new IllegalArgumentException("City cannot be empty");
            }
            if (dto.getCity().trim().length() < 2 || dto.getCity().trim().length() > 100) {
                throw new IllegalArgumentException("City name must be between 2 and 100 characters");
            }
        }
        
        // Validate coordinates if provided
        if (dto.getLatitude() != null || dto.getLongitude() != null) {
            Double lat = dto.getLatitude() != null ? dto.getLatitude() : existingTower.getLatitude();
            Double lon = dto.getLongitude() != null ? dto.getLongitude() : existingTower.getLongitude();
            validateCoordinates(lat, lon);
        }
        
        // Validate numeric fields if provided
        validateNumericFields(dto);
    }
    
    private void validateTowerEntity(Tower tower) {
        if (!tower.isValidCoordinates()) {
            throw new IllegalArgumentException("Invalid coordinates: latitude must be between -90 and 90, longitude must be between -180 and 180");
        }
        if (!tower.isValidBattery()) {
            throw new IllegalArgumentException("Invalid battery: must be between 0 and 100");
        }
        if (!tower.isValidTemperature()) {
            throw new IllegalArgumentException("Invalid temperature: must be between -273.15°C and 100°C");
        }
        if (!tower.isValidUptime()) {
            throw new IllegalArgumentException("Invalid uptime: must be between 0 and 100");
        }
        if (!tower.isValidNetworkLoad()) {
            throw new IllegalArgumentException("Invalid network load: must be between 0 and 100");
        }
    }
    
    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Both latitude and longitude are required");
        }
        
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees. Received: " + latitude);
        }
        
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees. Received: " + longitude);
        }
    }
    
    private void validateNumericFields(CreateTowerDTO dto) {
        if (dto.getBattery() != null && (dto.getBattery() < 0 || dto.getBattery() > 100)) {
            throw new IllegalArgumentException("Battery percentage must be between 0 and 100. Received: " + dto.getBattery());
        }
        
        if (dto.getTemperature() != null && (dto.getTemperature() < -273.15 || dto.getTemperature() > 100.0)) {
            throw new IllegalArgumentException("Temperature must be between -273.15°C and 100°C. Received: " + dto.getTemperature());
        }
        
        if (dto.getUptime() != null && (dto.getUptime() < 0 || dto.getUptime() > 100)) {
            throw new IllegalArgumentException("Uptime percentage must be between 0 and 100. Received: " + dto.getUptime());
        }
        
        if (dto.getNetworkLoad() != null && (dto.getNetworkLoad() < 0 || dto.getNetworkLoad() > 100)) {
            throw new IllegalArgumentException("Network load percentage must be between 0 and 100. Received: " + dto.getNetworkLoad());
        }
    }

    // Data cleanup methods
    public String cleanupInvalidData() {
        StringBuilder report = new StringBuilder();
        int fixedCount = 0;
        int deletedCount = 0;
        
        List<Tower> allTowers = towerRepository.findAll();
        
        for (Tower tower : allTowers) {
            boolean needsUpdate = false;
            
            // Check and fix coordinates
            if (!tower.isValidCoordinates()) {
                report.append("Tower ID ").append(tower.getId()).append(" (").append(tower.getName())
                      .append(") has invalid coordinates: lat=").append(tower.getLatitude())
                      .append(", lon=").append(tower.getLongitude()).append("\n");
                
                // Set default coordinates (New York City) if invalid
                if (tower.getLatitude() < -90.0 || tower.getLatitude() > 90.0) {
                    tower.setLatitude(40.7589);
                    needsUpdate = true;
                }
                if (tower.getLongitude() < -180.0 || tower.getLongitude() > 180.0) {
                    tower.setLongitude(-73.9851);
                    needsUpdate = true;
                }
                
                if (needsUpdate) {
                    tower.setName("Fixed " + tower.getName());
                    towerRepository.save(tower);
                    fixedCount++;
                    report.append("  -> Fixed coordinates to default values\n");
                }
            }
            
            // Check and fix other invalid fields
            if (!tower.isValidBattery() && tower.getBattery() != null) {
                tower.setBattery(100); // Set to 100% if invalid
                needsUpdate = true;
            }
            if (!tower.isValidTemperature() && tower.getTemperature() != null) {
                tower.setTemperature(20.0); // Set to 20°C if invalid
                needsUpdate = true;
            }
            if (!tower.isValidUptime() && tower.getUptime() != null) {
                tower.setUptime(100); // Set to 100% if invalid
                needsUpdate = true;
            }
            if (!tower.isValidNetworkLoad() && tower.getNetworkLoad() != null) {
                tower.setNetworkLoad(50); // Set to 50% if invalid
                needsUpdate = true;
            }
            
            // Check for obviously invalid names/cities
            if (tower.getName() != null && (tower.getName().length() < 2 || tower.getName().length() > 100)) {
                tower.setName("Tower " + tower.getId());
                needsUpdate = true;
            }
            if (tower.getCity() != null && (tower.getCity().length() < 2 || tower.getCity().length() > 100)) {
                tower.setCity("Unknown City");
                needsUpdate = true;
            }
            
            if (needsUpdate) {
                towerRepository.save(tower);
                fixedCount++;
            }
        }
        
        report.append("\nCleanup Summary:\n");
        report.append("- Fixed towers: ").append(fixedCount).append("\n");
        report.append("- Total towers processed: ").append(allTowers.size()).append("\n");
        
        return report.toString();
    }
    
    public void deleteInvalidTower(Long id) {
        Tower tower = towerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tower not found with id: " + id));
        
        if (!tower.isValidCoordinates()) {
            towerRepository.delete(tower);
            throw new RuntimeException("Deleted tower with ID " + id + " due to invalid coordinates");
        }
        
        throw new RuntimeException("Tower with ID " + id + " has valid coordinates and cannot be deleted");
    }
}
