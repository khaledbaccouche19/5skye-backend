package com.example.demo.repositories;

import com.example.demo.entities.EnvironmentalSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentalSensorRepository extends JpaRepository<EnvironmentalSensor, Long> {
}
