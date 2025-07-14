package com.example.demo.repositories;

import com.example.demo.entities.LEDDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LEDDisplayRepository extends JpaRepository<LEDDisplay, Long> {
}
