package com.example.demo.repositories;

import com.example.demo.entities.EquipmentMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentMetricsRepository extends JpaRepository<EquipmentMetrics, Long> {
}
