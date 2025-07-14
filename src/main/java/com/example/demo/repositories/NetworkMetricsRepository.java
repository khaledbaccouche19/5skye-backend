package com.example.demo.repositories;

import com.example.demo.entities.NetworkMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkMetricsRepository extends JpaRepository<NetworkMetrics, Long> {
}
