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
        registry.addMapping("/models/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Content-Length", "Content-Type", "Accept-Ranges", "Last-Modified")
                .maxAge(3600);
    }
}
