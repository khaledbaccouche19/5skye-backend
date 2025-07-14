package com.example.demo.repositories;

import com.example.demo.entities.TowerUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TowerUnitRepository extends JpaRepository<TowerUnit, Long> {
}
