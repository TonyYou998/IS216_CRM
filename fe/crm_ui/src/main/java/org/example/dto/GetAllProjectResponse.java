package org.example.dto;

import lombok.Data;

@Data
public class GetAllProjectResponse {
    String projectName;
    String startDate;
    String endDate;
    String leaderId;
    Long id;
//    List<Object> lstObject=new ArrayList<>();
}
