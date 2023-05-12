package com.uit.crm.user.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING_USER)
public class UserController {



    @GetMapping(Constants.GET_ALL_PROJECTS_BY_USER_ID)
    public ResponseEntity<Object> getAllProjectByUserId(@RequestHeader("Authorization") String request){

            List<ProjectDto> lstProject=SpringBeanUtil.getBean(ProjectService.class).findByUser(request);
            return ResponseHandler.getResponse(lstProject,HttpStatus.OK);
    }

}
