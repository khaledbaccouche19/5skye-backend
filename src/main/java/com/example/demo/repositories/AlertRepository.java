package com.example.demo.repositories;

import com.example.demo.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    
    List<Alert> findByTowerId(Long towerId);
    
    List<Alert> findByResolved(Boolean resolved);
    
    List<Alert> findTop10ByOrderByTimestampDesc();
    
    void deleteByTowerId(Long towerId);
}
