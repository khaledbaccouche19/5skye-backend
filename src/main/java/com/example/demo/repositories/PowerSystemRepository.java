package com.example.demo.repositories;

import com.example.demo.entities.PowerSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerSystemRepository extends JpaRepository<PowerSystem, Long> {
}
