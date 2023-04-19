package com.uit.crm.task.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private String taskName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String assigneeEmployeeId;
    private boolean status;
    private String projectId;
}
