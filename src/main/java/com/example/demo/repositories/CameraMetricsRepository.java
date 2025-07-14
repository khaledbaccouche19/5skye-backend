package com.example.demo.repositories;

import com.example.demo.entities.CameraMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraMetricsRepository extends JpaRepository<CameraMetrics, Long> {
}
