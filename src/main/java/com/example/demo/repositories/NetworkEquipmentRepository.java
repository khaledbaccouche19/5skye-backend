package com.example.demo.repositories;

import com.example.demo.entities.NetworkEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkEquipmentRepository extends JpaRepository<NetworkEquipment, Long> {
}
