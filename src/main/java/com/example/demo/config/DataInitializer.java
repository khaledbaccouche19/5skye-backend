package com.example.demo.config;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = User.builder()
                    .username("admin")
                    .email("admin@intellitwin.com")
                    .passwordHash(passwordEncoder.encode("demo123"))
                    .firstName("Admin")
                    .lastName("User")
                    .role("ADMIN")
                    .isActive(true)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build();

            userRepository.save(adminUser);
            System.out.println("Default admin user created: admin/demo123");
        }

        // Create default operator user if it doesn't exist
        if (!userRepository.existsByUsername("operator")) {
            User operatorUser = User.builder()
                    .username("operator")
                    .email("operator@intellitwin.com")
                    .passwordHash(passwordEncoder.encode("demo123"))
                    .firstName("Operator")
                    .lastName("User")
                    .role("USER")
                    .isActive(true)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build();

            userRepository.save(operatorUser);
            System.out.println("Default operator user created: operator/demo123");
        }
    }
}
