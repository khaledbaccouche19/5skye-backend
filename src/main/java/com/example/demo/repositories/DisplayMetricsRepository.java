package com.example.demo.repositories;

import com.example.demo.entities.DisplayMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayMetricsRepository extends JpaRepository<DisplayMetrics, Long> {
}
