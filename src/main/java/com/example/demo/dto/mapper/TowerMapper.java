package com.example.demo.dto.mapper;

import com.example.demo.dto.tower.TowerSummaryDTO;
import com.example.demo.dto.tower.TowerDetailDTO;
import com.example.demo.entities.Tower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TowerMapper {
    TowerSummaryDTO toSummaryDto(Tower entity);
    TowerDetailDTO toDetailDto(Tower entity);
    List<TowerSummaryDTO> toSummaryDtoList(List<Tower> entities);
    List<TowerDetailDTO> toDetailDtoList(List<Tower> entities);
    Tower toEntity(com.example.demo.dto.tower.CreateTowerDTO dto);
    Tower toEntity(TowerDetailDTO dto);
} 