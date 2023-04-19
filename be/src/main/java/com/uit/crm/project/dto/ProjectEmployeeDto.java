package com.uit.crm.project.dto;

import com.uit.crm.common.BaseDto;
import lombok.Data;

@Data
public class ProjectEmployeeDto extends BaseDto {
        private String projectId;
        private String userId;

}
