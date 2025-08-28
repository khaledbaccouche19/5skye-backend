package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Value("${app.upload.path:uploads/models}")
    private String uploadPath;

    @PostMapping("/model")
    public ResponseEntity<String> uploadModel(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file type
            if (!isValidModelFile(file)) {
                return ResponseEntity.badRequest().body("Invalid file type. Only GLB files are allowed.");
            }

            // Create upload directory if it doesn't exist
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            
            // Save file
            Path filePath = uploadDir.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);

            // Return the relative path for database storage
            String relativePath = "/models/" + uniqueFilename;
            
            return ResponseEntity.ok(relativePath);
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/model")
    public ResponseEntity<String> deleteModel(@RequestParam("path") String modelPath) {
        try {
            // Remove leading slash if present
            if (modelPath.startsWith("/")) {
                modelPath = modelPath.substring(1);
            }
            
            Path filePath = Paths.get(uploadPath, modelPath);
            
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return ResponseEntity.ok("Model deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to delete file: " + e.getMessage());
        }
    }

    private boolean isValidModelFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) return false;
        
        String extension = getFileExtension(filename).toLowerCase();
        return extension.equals(".glb") || extension.equals(".gltf");
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
