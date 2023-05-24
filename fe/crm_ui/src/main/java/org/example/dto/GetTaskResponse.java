package org.example.dto;

import lombok.Data;

@Data
public class GetTaskResponse {
    String taskName;
    String startDate;
    String endDate;
    String assigneeEmployeeId;
    String status;
    String projectId;
    int id;
}
