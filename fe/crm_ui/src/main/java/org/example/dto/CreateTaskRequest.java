package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateTaskRequest {
    private String taskName;
    private String startDate;
    private String endDate;
    private String assigneeEmployeeId;
    private String projectId;
}
