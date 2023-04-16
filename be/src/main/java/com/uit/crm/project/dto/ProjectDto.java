package com.uit.crm.project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectDto {
    private String projectName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String leaderId;
}
