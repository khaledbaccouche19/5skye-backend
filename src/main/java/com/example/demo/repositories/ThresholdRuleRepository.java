package com.example.demo.repositories;

import com.example.demo.entities.ThresholdRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThresholdRuleRepository extends JpaRepository<ThresholdRule, Long> {
    
    List<ThresholdRule> findByTowerId(Long towerId);
    
    void deleteByTowerId(Long towerId);
}
