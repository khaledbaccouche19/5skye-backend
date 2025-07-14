package com.example.demo.service;

import com.example.demo.entities.SecurityEvent;
import com.example.demo.repositories.SecurityEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityEventService {

    private final SecurityEventRepository repository;

    @Autowired
    public SecurityEventService(SecurityEventRepository repository) {
        this.repository = repository;
    }

    public SecurityEvent save(SecurityEvent event) {
        return repository.save(event);
    }

    public List<SecurityEvent> findAll() {
        return repository.findAll();
    }

    public SecurityEvent findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public SecurityEvent update(Long id, SecurityEvent updated) {
        return repository.findById(id).map(existing -> {
            existing.setDescription(updated.getDescription());
            existing.setEventTime(updated.getEventTime());
            existing.setSeverity(updated.getSeverity());
            existing.setVideoFilePath(updated.getVideoFilePath());
            existing.setImageFilePath(updated.getImageFilePath());
            existing.setAudioFilePath(updated.getAudioFilePath());
            existing.setEventLocation(updated.getEventLocation());
            existing.setDetectionAlgorithm(updated.getDetectionAlgorithm());
            existing.setConfidenceScore(updated.getConfidenceScore());
            existing.setObjectsDetected(updated.getObjectsDetected());
            existing.setFaceRecognitionResult(updated.getFaceRecognitionResult());
            existing.setLicensePlateNumber(updated.getLicensePlateNumber());
            existing.setBehaviorAnalysis(updated.getBehaviorAnalysis());
            existing.setResponseAction(updated.getResponseAction());
            existing.setAssignedTo(updated.getAssignedTo());
            existing.setAcknowledgedAt(updated.getAcknowledgedAt());
            existing.setResolvedAt(updated.getResolvedAt());
            existing.setResolutionNotes(updated.getResolutionNotes());
            existing.setIsResolved(updated.getIsResolved());
            existing.setRequiresFollowUp(updated.getRequiresFollowUp());
            existing.setFollowUpActions(updated.getFollowUpActions());
            existing.setRelatedIncidents(updated.getRelatedIncidents());
            existing.setEvidenceCollected(updated.getEvidenceCollected());
            existing.setReportGenerated(updated.getReportGenerated());
            existing.setCamera(updated.getCamera());
            existing.setAssignedUser(updated.getAssignedUser());
            return repository.save(existing);
        }).orElse(null);
    }
}
