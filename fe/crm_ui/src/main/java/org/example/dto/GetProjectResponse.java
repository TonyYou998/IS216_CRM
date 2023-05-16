package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetProjectResponse {
    String projectName;
    String startDate;
    String endDate;
    String leaderId;
    Long id;
//    List<Object> lstObject=new ArrayList<>();
}
