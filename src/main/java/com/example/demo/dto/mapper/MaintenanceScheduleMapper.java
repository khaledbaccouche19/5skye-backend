package com.example.demo.dto.mapper;

import com.example.demo.dto.maintenance.MaintenanceScheduleDTO;
import com.example.demo.dto.maintenance.CreateMaintenanceScheduleDTO;
import com.example.demo.entities.MaintenanceSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceScheduleMapper {
    MaintenanceScheduleDTO toDto(MaintenanceSchedule entity);
    MaintenanceSchedule toEntity(CreateMaintenanceScheduleDTO dto);
    List<MaintenanceScheduleDTO> toDtoList(List<MaintenanceSchedule> entities);
} 