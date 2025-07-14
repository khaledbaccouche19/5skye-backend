package com.example.demo.dto.tower;

import com.example.demo.dto.unit.TowerUnitDTO;
import com.example.demo.dto.alert.AlertDTO;
import com.example.demo.dto.maintenance.MaintenanceScheduleDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TowerDetailDTO {
    private Long id;
    private String name;
    private String location;
    private String status;
    private List<TowerUnitDTO> units;
    private List<AlertDTO> alerts;
    private List<MaintenanceScheduleDTO> maintenanceSchedules;
} 