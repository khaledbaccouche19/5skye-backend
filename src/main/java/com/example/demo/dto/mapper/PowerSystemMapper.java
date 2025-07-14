package com.example.demo.dto.mapper;

import com.example.demo.dto.systems.PowerSystemDTO;
import com.example.demo.dto.systems.CreatePowerSystemDTO;
import com.example.demo.entities.PowerSystem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PowerSystemMapper {
    PowerSystemDTO toDto(PowerSystem entity);
    PowerSystem toEntity(CreatePowerSystemDTO dto);
    PowerSystem toEntity(PowerSystemDTO dto);
    List<PowerSystemDTO> toDtoList(List<PowerSystem> entities);
} 