package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "batteries")
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batteryId;

    @Column(nullable = false)
    private String batteryType; // NMC, LiFePO4, etc.

    @Column(nullable = false)
    private Integer capacity; // Ah

    @Column(nullable = false)
    private Double voltage; // V

    @Column(nullable = false)
    private Double currentCharge; // Current charge percentage

    private String status;
    private String serialNumber;
    private LocalDate installationDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}