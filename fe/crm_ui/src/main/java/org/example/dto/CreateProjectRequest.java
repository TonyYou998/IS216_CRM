package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateProjectRequest {
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String leaderId;
}
