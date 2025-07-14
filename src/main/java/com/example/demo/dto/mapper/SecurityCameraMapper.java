package com.example.demo.dto.mapper;

import com.example.demo.dto.systems.SecurityCameraDTO;
import com.example.demo.dto.systems.CreateSecurityCameraDTO;
import com.example.demo.entities.SecurityCamera;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SecurityCameraMapper {
    SecurityCameraDTO toDto(SecurityCamera entity);
    SecurityCamera toEntity(CreateSecurityCameraDTO dto);
    List<SecurityCameraDTO> toDtoList(List<SecurityCamera> entities);
} 