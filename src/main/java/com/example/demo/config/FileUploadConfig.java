package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Value("${app.upload.path:uploads/models}")
    private String uploadPath;

    @Value("${app.cors.allowed-origins:*}")
    private String allowedOriginsRaw;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get absolute path for uploads directory
        String absolutePath = new File(uploadPath).getAbsolutePath();
        
        // Serve uploaded 3D model files from the uploads directory
        registry.addResourceHandler("/models/**")
                .addResourceLocations("file:" + absolutePath + "/")
                .setCachePeriod(3600) // Cache for 1 hour
                .addResourceLocations("classpath:/static/models/"); // Fallback to static resources
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = (allowedOriginsRaw == null || allowedOriginsRaw.isBlank())
                ? new String[]{"*"}
                : java.util.Arrays.stream(allowedOriginsRaw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);

        registry.addMapping("/api/**")
                .allowedOrigins(origins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Content-Length", "Content-Type")
                .maxAge(3600);

        registry.addMapping("/models/**")
                .allowedOrigins(origins)
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Content-Length", "Content-Type", "Accept-Ranges", "Last-Modified")
                .maxAge(3600);
    }
}
