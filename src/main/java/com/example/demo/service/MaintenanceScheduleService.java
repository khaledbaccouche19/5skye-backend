package com.example.demo.service;

import com.example.demo.entities.MaintenanceSchedule;
import com.example.demo.repositories.MaintenanceScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceScheduleService {

    private final MaintenanceScheduleRepository repository;

    @Autowired
    public MaintenanceScheduleService(MaintenanceScheduleRepository repository) {
        this.repository = repository;
    }

    public MaintenanceSchedule save(MaintenanceSchedule schedule) {
        return repository.save(schedule);
    }

    public List<MaintenanceSchedule> findAll() {
        return repository.findAll();
    }

    public MaintenanceSchedule findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public MaintenanceSchedule update(Long id, MaintenanceSchedule updated) {
        return repository.findById(id).map(existing -> {
            existing.setDescription(updated.getDescription());
            existing.setScheduledDate(updated.getScheduledDate());
            existing.setStatus(updated.getStatus());
            existing.setTower(updated.getTower());
            return repository.save(existing);
        }).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
