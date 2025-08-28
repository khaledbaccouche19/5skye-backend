package com.example.demo.repositories;

import com.example.demo.entities.Tower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TowerRepository extends JpaRepository<Tower, Long> {
    
    @Query("SELECT t FROM Tower t " +
           "LEFT JOIN FETCH t.hardware " +
           "WHERE t.id = :id")
    Optional<Tower> findByIdWithHardware(@Param("id") Long id);
    
    @Query("SELECT t FROM Tower t " +
           "LEFT JOIN FETCH t.alerts " +
           "WHERE t.id = :id")
    Optional<Tower> findByIdWithAlerts(@Param("id") Long id);
    
    @Query("SELECT t FROM Tower t " +
           "LEFT JOIN FETCH t.thresholdRules " +
           "WHERE t.id = :id")
    Optional<Tower> findByIdWithThresholdRules(@Param("id") Long id);
    
    @Query("SELECT t FROM Tower t " +
           "LEFT JOIN FETCH t.telemetryData " +
           "WHERE t.id = :id")
    Optional<Tower> findByIdWithTelemetryData(@Param("id") Long id);
}
