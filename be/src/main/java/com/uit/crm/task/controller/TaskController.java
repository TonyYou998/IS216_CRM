package com.uit.crm.task.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.task.dto.TaskDto;
import com.uit.crm.task.service.TaskService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING_USER)
public class TaskController {

    @PostMapping(Constants.REQUEST_MAPPING_TASK+Constants.LEADER+Constants.CREATE_TASK)
    public ResponseEntity<Object> createTask(@RequestBody TaskDto request){
        TaskDto response= SpringBeanUtil.getBean(TaskService.class).createTask(request);
        if(response!=null)
            return ResponseHandler.getResponse(response, HttpStatus.OK);
        return ResponseHandler.getResponse("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @PutMapping(Constants.REQUEST_MAPPING_TASK+Constants.LEADER+Constants.ASSIGN_TASK)
    public ResponseEntity<Object> assignTask(@RequestBody TaskDto request){
            TaskDto response=SpringBeanUtil.getBean(TaskService.class).assignTask(request);
            if(response!=null)
                return ResponseHandler.getResponse(response,HttpStatus.OK);
            return ResponseHandler.getResponse("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping(Constants.REQUEST_MAPPING_TASK+Constants.GET_TASK_BY_PROJECT_ID)
    public ResponseEntity<Object> getTaskByProjectId(@RequestParam("id") String projectId,@RequestHeader("Authorization") String token ){

        List<TaskDto> response=SpringBeanUtil.getBean(TaskService.class).getTaskByProjectId(projectId,token);
        if(response!=null)
            return ResponseHandler.getResponse(response,HttpStatus.OK);
        return  ResponseHandler.getResponse("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<Object> getTaskByUserId(){

    }

}