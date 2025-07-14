package com.example.demo.dto.alert;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAlertDTO {
    @NotNull
    private String message;
    @NotNull
    private String severity;
    @NotNull
    private String source;
    private String status;
    private String type;
    private java.time.LocalDateTime timestamp;
} 