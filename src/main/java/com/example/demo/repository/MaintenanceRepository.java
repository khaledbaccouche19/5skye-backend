package com.example.demo.repository;

import com.example.demo.entities.Maintenance;
import com.example.demo.entities.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    
    List<Maintenance> findByTowerIdOrderByStartDateDesc(Long towerId);
    
    List<Maintenance> findByStatusOrderByStartDateAsc(MaintenanceStatus status);
    
    List<Maintenance> findByTowerIdAndStatusOrderByStartDateDesc(Long towerId, MaintenanceStatus status);
    
    @Query("SELECT m FROM Maintenance m WHERE m.tower.id = :towerId AND m.startDate >= :startDate AND m.startDate <= :endDate ORDER BY m.startDate DESC")
    List<Maintenance> findByTowerIdAndDateRange(@Param("towerId") Long towerId, 
                                               @Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
    
    @Query("SELECT m FROM Maintenance m WHERE m.status = :status AND m.startDate <= :currentDate ORDER BY m.startDate ASC")
    List<Maintenance> findOverdueMaintenance(@Param("status") MaintenanceStatus status, 
                                           @Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT m FROM Maintenance m WHERE m.nextMaintenanceDate <= :currentDate AND m.isRecurring = true ORDER BY m.nextMaintenanceDate ASC")
    List<Maintenance> findUpcomingRecurringMaintenance(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.tower.id = :towerId AND m.status = :status")
    Long countByTowerIdAndStatus(@Param("towerId") Long towerId, @Param("status") MaintenanceStatus status);
}
