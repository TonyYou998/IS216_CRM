package org.example.dto;

import lombok.Data;

@Data
public class GetTaskResponse {
    String taskName;
    String startDate;
    String endDate;
    String assigneeEmployeeId;
    String assignEmployeeName;
    String status;
    String projectId;
    private  String description;
    int id;
}
