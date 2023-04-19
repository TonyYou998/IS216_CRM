package com.uit.crm.task.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.task.dto.TaskDto;
import com.uit.crm.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING_USER)
public class TaskController {

    @PostMapping(Constants.REQUEST_MAPPING_TASK+ Constants.CREATE_TASK)
    public ResponseEntity<Object> createTask(@RequestBody TaskDto request){
        TaskDto response= SpringBeanUtil.getBean(TaskService.class).createTask(request);
        if(response!=null)
            return ResponseHandler.getResponse(response, HttpStatus.OK);
        return ResponseHandler.getResponse("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);


    }

}
