package com.example.demo.service;

import com.example.demo.entities.Equipment;
import com.example.demo.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository repository;

    @Autowired
    public EquipmentService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public Equipment save(Equipment equipment) {
        return repository.save(equipment);
    }

    public List<Equipment> findAll() {
        return repository.findAll();
    }

    public Equipment findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Equipment update(Long id, Equipment updated) {
        return repository.findById(id).map(existing -> {
            existing.setCategory(updated.getCategory());
            existing.setEquipmentName(updated.getEquipmentName());
            existing.setEquipmentType(updated.getEquipmentType());
            existing.setManufacturer(updated.getManufacturer());
            existing.setModel(updated.getModel());
            existing.setStatus(updated.getStatus());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setDescription(updated.getDescription());
            existing.setSpecifications(updated.getSpecifications());
            existing.setPartNumber(updated.getPartNumber());
            existing.setAssetTag(updated.getAssetTag());
            existing.setLocation(updated.getLocation());
            existing.setInstallationPosition(updated.getInstallationPosition());
            existing.setPowerRequirement(updated.getPowerRequirement());
            existing.setOperatingVoltage(updated.getOperatingVoltage());
            existing.setOperatingCurrent(updated.getOperatingCurrent());
            existing.setPowerConsumption(updated.getPowerConsumption());
            existing.setOperatingTemperature(updated.getOperatingTemperature());
            existing.setStorageTemperature(updated.getStorageTemperature());
            existing.setOperatingHumidity(updated.getOperatingHumidity());
            existing.setDimensions(updated.getDimensions());
            existing.setWeight(updated.getWeight());
            existing.setMaterial(updated.getMaterial());
            existing.setColor(updated.getColor());
            existing.setFinishType(updated.getFinishType());
            existing.setProtectionRating(updated.getProtectionRating());
            existing.setCertifications(updated.getCertifications());
            existing.setComplianceStandards(updated.getComplianceStandards());
            existing.setWarrantyPeriod(updated.getWarrantyPeriod());
            existing.setWarrantyProvider(updated.getWarrantyProvider());
            existing.setServiceProvider(updated.getServiceProvider());
            existing.setSupportContact(updated.getSupportContact());
            existing.setDocumentationPath(updated.getDocumentationPath());
            existing.setManualPath(updated.getManualPath());
            existing.setDrawingPath(updated.getDrawingPath());
            existing.setCertificatePath(updated.getCertificatePath());
            existing.setPurchaseOrder(updated.getPurchaseOrder());
            existing.setVendor(updated.getVendor());
            existing.setPurchaseDate(updated.getPurchaseDate());
            existing.setPurchasePrice(updated.getPurchasePrice());
            existing.setDeliveryDate(updated.getDeliveryDate());
            existing.setAcceptanceDate(updated.getAcceptanceDate());
            existing.setCommissioningDate(updated.getCommissioningDate());
            existing.setInstallationDate(updated.getInstallationDate());
            existing.setInstallationBy(updated.getInstallationBy());
            existing.setInstallationNotes(updated.getInstallationNotes());
            existing.setLastMaintenanceDate(updated.getLastMaintenanceDate());
            existing.setNextMaintenanceDate(updated.getNextMaintenanceDate());
            existing.setMaintenanceSchedule(updated.getMaintenanceSchedule());
            existing.setMaintenanceProvider(updated.getMaintenanceProvider());
            existing.setMaintenanceNotes(updated.getMaintenanceNotes());
            existing.setSpareParts(updated.getSpareParts());
            existing.setSparePartsLocation(updated.getSparePartsLocation());
            existing.setTroubleshootingGuide(updated.getTroubleshootingGuide());
            existing.setReplacementProcedure(updated.getReplacementProcedure());
            existing.setDisposalProcedure(updated.getDisposalProcedure());
            existing.setEnvironmentalImpact(updated.getEnvironmentalImpact());
            existing.setRecyclingInformation(updated.getRecyclingInformation());
            existing.setRiskAssessment(updated.getRiskAssessment());
            existing.setSafetyPrecautions(updated.getSafetyPrecautions());
            existing.setOperatingProcedure(updated.getOperatingProcedure());
            existing.setEmergencyProcedure(updated.getEmergencyProcedure());
            existing.setCalibrationRequirement(updated.getCalibrationRequirement());
            existing.setCalibrationSchedule(updated.getCalibrationSchedule());
            existing.setCalibrationProvider(updated.getCalibrationProvider());
            existing.setLastCalibrationDate(updated.getLastCalibrationDate());
            existing.setNextCalibrationDate(updated.getNextCalibrationDate());
            existing.setCalibrationCertificate(updated.getCalibrationCertificate());
            existing.setPerformanceMonitoring(updated.getPerformanceMonitoring());
            existing.setAlarmConfiguration(updated.getAlarmConfiguration());
            existing.setEventLogging(updated.getEventLogging());
            existing.setDataRetention(updated.getDataRetention());
            existing.setBackupConfiguration(updated.getBackupConfiguration());
            existing.setSecurityConfiguration(updated.getSecurityConfiguration());
            existing.setAccessControl(updated.getAccessControl());
            existing.setNetworkConfiguration(updated.getNetworkConfiguration());
            existing.setIpAddress(updated.getIpAddress());
            existing.setMacAddress(updated.getMacAddress());
            existing.setFirmwareVersion(updated.getFirmwareVersion());
            existing.setSoftwareVersion(updated.getSoftwareVersion());
            existing.setConfigurationVersion(updated.getConfigurationVersion());
            existing.setLicenseInformation(updated.getLicenseInformation());
            existing.setUpdateSchedule(updated.getUpdateSchedule());
            existing.setPatchManagement(updated.getPatchManagement());
            existing.setVulnerabilityAssessment(updated.getVulnerabilityAssessment());
            existing.setSecurityAudit(updated.getSecurityAudit());
            existing.setComplianceAudit(updated.getComplianceAudit());
            existing.setPerformanceAudit(updated.getPerformanceAudit());
            existing.setQualityAssurance(updated.getQualityAssurance());
            existing.setChangeManagement(updated.getChangeManagement());
            existing.setConfigurationManagement(updated.getConfigurationManagement());
            existing.setAssetManagement(updated.getAssetManagement());
            existing.setLifecycleManagement(updated.getLifecycleManagement());
            existing.setReplacementPlan(updated.getReplacementPlan());
            existing.setUpgradeSchedule(updated.getUpgradeSchedule());
            existing.setMigrationPlan(updated.getMigrationPlan());
            existing.setRetirementPlan(updated.getRetirementPlan());
            existing.setUnit(updated.getUnit());
            return repository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
