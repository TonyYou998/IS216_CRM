package com.uit.crm.project.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.dto.ProjectEmployeeDto;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING)
public class ProjectController {
        @PostMapping(Constants.CREATE_PROJECT)
        public ResponseEntity<Object> createProject(@RequestBody ProjectDto request){
            ProjectDto response= SpringBeanUtil.getBean(ProjectService.class).createProject(request);
            if(response!=null)
                return  ResponseHandler.getResponse(response,HttpStatus.OK);
            return ResponseHandler.getResponse("An unexpected error occurred while processing your request. Please try again later or contact the support team for assistance.",HttpStatus.INTERNAL_SERVER_ERROR);

        }
        @GetMapping(Constants.ADD_EMPLOYEE)
    public  ResponseEntity<Object> addEmployee(@RequestParam("userId")String userId, @RequestParam("projectId") String projectId){

            ProjectEmployeeDto response=SpringBeanUtil.getBean(ProjectService.class).addEmployee(userId,projectId);
            if(response!=null)
                return ResponseHandler.getResponse(response, HttpStatus.OK);
            return ResponseHandler.getResponse("An unexpected error occurred while processing your request. Please try again later or contact the support team for assistance.",HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
