package com.example.demo.dto.mapper;

import com.example.demo.dto.equipment.EquipmentDTO;
import com.example.demo.entities.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    EquipmentDTO toDto(Equipment entity);
    List<EquipmentDTO> toDtoList(List<Equipment> entities);
} 