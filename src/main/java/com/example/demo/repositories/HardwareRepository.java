package com.example.demo.repositories;

import com.example.demo.entities.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long> {
    
    List<Hardware> findByTowerId(Long towerId);
    
    void deleteByTowerId(Long towerId);
}
