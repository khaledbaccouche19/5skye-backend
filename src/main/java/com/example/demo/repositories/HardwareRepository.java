package com.example.demo.repositories;

import com.example.demo.entities.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long> {
    
    List<Hardware> findByTowerId(Long towerId);
    
    void deleteByTowerId(Long towerId);

    @Query("SELECT h FROM Hardware h " +
           "WHERE (:towerId IS NULL OR h.tower.id = :towerId) " +
           "AND (:vendor IS NULL OR LOWER(h.vendor) LIKE LOWER(CONCAT('%', :vendor, '%'))) " +
           "AND (:serial IS NULL OR LOWER(h.serialNumber) LIKE LOWER(CONCAT('%', :serial, '%'))) " +
           "AND (:warrantyAfter IS NULL OR h.warrantyExpiry >= :warrantyAfter) " +
           "AND (:warrantyBefore IS NULL OR h.warrantyExpiry <= :warrantyBefore)")
    List<Hardware> searchHardware(
            @Param("towerId") Long towerId,
            @Param("vendor") String vendor,
            @Param("serial") String serial,
            @Param("warrantyAfter") LocalDate warrantyAfter,
            @Param("warrantyBefore") LocalDate warrantyBefore
    );
}
