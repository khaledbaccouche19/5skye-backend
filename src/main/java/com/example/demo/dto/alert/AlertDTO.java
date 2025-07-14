package com.example.demo.dto.alert;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AlertDTO {
    private Long id;
    private String message;
    private String severity;
    private String source;
    private String status;
    private String type;
    private String tower;
    private LocalDateTime timestamp;
}
