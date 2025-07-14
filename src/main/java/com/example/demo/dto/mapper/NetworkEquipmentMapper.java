package com.example.demo.dto.mapper;

import com.example.demo.dto.systems.NetworkEquipmentDTO;
import com.example.demo.dto.systems.CreateNetworkEquipmentDTO;
import com.example.demo.entities.NetworkEquipment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NetworkEquipmentMapper {
    NetworkEquipmentDTO toDto(NetworkEquipment entity);
    NetworkEquipment toEntity(CreateNetworkEquipmentDTO dto);
    List<NetworkEquipmentDTO> toDtoList(List<NetworkEquipment> entities);
} 