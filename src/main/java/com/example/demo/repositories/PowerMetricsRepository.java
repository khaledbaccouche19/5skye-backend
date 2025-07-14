package com.example.demo.repositories;

import com.example.demo.entities.PowerMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerMetricsRepository extends JpaRepository<PowerMetrics, Long> {
}
