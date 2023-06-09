package org.example.dto;

import lombok.Data;

@Data
public class CreateTaskResponse {
    private String taskName;
    private String startDate;
    private String endDate;
    private String assigneeEmployeeId;
    private String status;
    private String projectId;
    private String id;
}
