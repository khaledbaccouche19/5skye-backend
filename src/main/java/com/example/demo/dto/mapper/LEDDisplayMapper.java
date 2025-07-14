package com.example.demo.dto.mapper;

import com.example.demo.dto.systems.LEDDisplayDTO;
import com.example.demo.dto.systems.CreateLEDDisplayDTO;
import com.example.demo.entities.LEDDisplay;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LEDDisplayMapper {
    LEDDisplayDTO toDto(LEDDisplay entity);
    LEDDisplay toEntity(CreateLEDDisplayDTO dto);
    LEDDisplay toEntity(LEDDisplayDTO dto);
    List<LEDDisplayDTO> toDtoList(List<LEDDisplay> entities);
} 