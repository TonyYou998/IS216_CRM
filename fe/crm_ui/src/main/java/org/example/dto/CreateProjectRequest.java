package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateProjectRequest {
    private String projectName;
    private String startDate;
    private String endDate;
    private String leaderId;

}
