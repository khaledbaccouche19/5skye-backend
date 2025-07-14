package com.example.demo.dto.mapper;

import com.example.demo.dto.unit.TowerUnitDTO;
import com.example.demo.entities.TowerUnit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TowerUnitMapper {
    TowerUnitDTO toDto(TowerUnit entity);
    List<TowerUnitDTO> toDtoList(List<TowerUnit> entities);
} 