package com.example.demo.dto.mapper;

import com.example.demo.dto.systems.EnvironmentalSensorDTO;
import com.example.demo.dto.systems.CreateSensorDTO;
import com.example.demo.entities.EnvironmentalSensor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SensorMapper {
    EnvironmentalSensorDTO toDto(EnvironmentalSensor entity);
    EnvironmentalSensor toEntity(CreateSensorDTO dto);
    List<EnvironmentalSensorDTO> toDtoList(List<EnvironmentalSensor> entities);
} 