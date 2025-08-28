package com.example.demo.repositories;

import com.example.demo.entities.TelemetryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetryDataRepository extends JpaRepository<TelemetryData, Long> {
    
    List<TelemetryData> findByTowerIdOrderByTimestampDesc(Long towerId);
    
    @Query("SELECT t FROM TelemetryData t WHERE t.towerId = :towerId AND t.timestamp >= :startTime ORDER BY t.timestamp ASC")
    List<TelemetryData> findByTowerIdAndTimestampAfterOrderByTimestampAsc(@Param("towerId") Long towerId, @Param("startTime") Instant startTime);
    
    @Query("SELECT t FROM TelemetryData t WHERE t.towerId = :towerId AND t.timestamp BETWEEN :startTime AND :endTime ORDER BY t.timestamp ASC")
    List<TelemetryData> findByTowerIdAndTimestampBetweenOrderByTimestampAsc(@Param("towerId") Long towerId, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
    
    void deleteByTowerId(Long towerId);

    // New methods for comprehensive telemetry data
    Optional<TelemetryData> findTopByTowerIdOrderByTimestampDesc(Long towerId);
    
    List<TelemetryData> findByTowerIdAndStatusOrderByTimestampDesc(Long towerId, String status);
    
    @Query("SELECT t FROM TelemetryData t WHERE t.towerId = :towerId AND t.status = :status AND t.timestamp >= :startTime ORDER BY t.timestamp DESC")
    List<TelemetryData> findByTowerIdAndStatusAndTimestampAfterOrderByTimestampDesc(
            @Param("towerId") Long towerId, 
            @Param("status") String status, 
            @Param("startTime") Instant startTime);
}
