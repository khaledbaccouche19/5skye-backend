package com.example.demo.service;

import com.example.demo.entities.Battery;
import com.example.demo.repositories.BatteryRepository;
import com.example.demo.dto.systems.BatteryDTO;
import com.example.demo.dto.systems.CreateBatteryDTO;
import com.example.demo.dto.mapper.BatteryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryService {

    private final BatteryRepository batteryRepository;

    @Autowired
    private BatteryMapper batteryMapper;

    @Autowired
    public BatteryService(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    public List<BatteryDTO> getAllBatteryDTOs() {
        return batteryMapper.toDtoList(batteryRepository.findAll());
    }

    public Battery save(CreateBatteryDTO dto) {
        Battery battery = batteryMapper.toEntity(dto);
        System.out.println("DEBUG: dto.batteryType = " + dto.getBatteryType());
        System.out.println("DEBUG: dto.capacity = " + dto.getCapacity());
        System.out.println("DEBUG: dto.voltage = " + dto.getVoltage());
        System.out.println("DEBUG: dto.currentCharge = " + dto.getCurrentCharge());
        System.out.println("DEBUG: dto.status = " + dto.getStatus());
        System.out.println("DEBUG: dto.serialNumber = " + dto.getSerialNumber());
        System.out.println("DEBUG: dto.installationDate = " + dto.getInstallationDate());
        System.out.println("DEBUG: battery.batteryType = " + battery.getBatteryType());
        System.out.println("DEBUG: battery.capacity = " + battery.getCapacity());
        System.out.println("DEBUG: battery.voltage = " + battery.getVoltage());
        System.out.println("DEBUG: battery.currentCharge = " + battery.getCurrentCharge());
        System.out.println("DEBUG: battery.status = " + battery.getStatus());
        System.out.println("DEBUG: battery.serialNumber = " + battery.getSerialNumber());
        System.out.println("DEBUG: battery.installationDate = " + battery.getInstallationDate());
        // Manual mapping for debug (should not be needed, but keep for now)
        battery.setBatteryType(dto.getBatteryType());
        battery.setCapacity(dto.getCapacity());
        battery.setVoltage(dto.getVoltage());
        battery.setCurrentCharge(dto.getCurrentCharge());
        battery.setStatus(dto.getStatus());
        battery.setSerialNumber(dto.getSerialNumber());
        battery.setInstallationDate(dto.getInstallationDate());
        return batteryRepository.save(battery);
    }

    public List<Battery> findAll() {
        return batteryRepository.findAll();
    }

    public Battery findById(Long id) {
        return batteryRepository.findById(id).orElse(null);
    }

    public Battery update(Long id, BatteryDTO dto) {
        Battery existing = batteryRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        // Only update fields if they are not null in the DTO
        if (dto.getBatteryType() != null) {
            existing.setBatteryType(dto.getBatteryType());
        }
        if (dto.getCapacity() != null) {
            existing.setCapacity(dto.getCapacity());
        }
        if (dto.getVoltage() != null) {
            existing.setVoltage(dto.getVoltage());
        }
        if (dto.getCurrentCharge() != null) {
            existing.setCurrentCharge(dto.getCurrentCharge());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getSerialNumber() != null) {
            existing.setSerialNumber(dto.getSerialNumber());
        }
        if (dto.getInstallationDate() != null) {
            existing.setInstallationDate(dto.getInstallationDate());
        }
        return batteryRepository.save(existing);
    }

    public void delete(Long id) {
        batteryRepository.deleteById(id);
    }

    public BatteryDTO toDto(Battery battery) {
        return batteryMapper.toDto(battery);
    }
}
