package com.example.demo.dto.mapper;

import com.example.demo.dto.systems.BatteryDTO;
import com.example.demo.dto.systems.CreateBatteryDTO;
import com.example.demo.entities.Battery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BatteryMapper {
    BatteryDTO toDto(Battery entity);
    Battery toEntity(CreateBatteryDTO dto);
    Battery toEntity(BatteryDTO dto);
    List<BatteryDTO> toDtoList(List<Battery> entities);
} 