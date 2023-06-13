package com.uit.crm.user.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.model.User;
import com.uit.crm.user.service.UserService;

import com.uit.crm.common.utils.SpringBeanUtil;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING)
public class AdminController {

    @PostMapping(Constants.CREATE_USER)
    public ResponseEntity<Object> createUserAccount(@RequestBody GetUserDto request){

            UserDto response= SpringBeanUtil.getBean(UserService.class).createUser(request);
            if(response!=null)
                return ResponseHandler.getResponse(response,HttpStatus.OK);
            return ResponseHandler.getResponse("INTERNAL SERVER ERROR",HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PostMapping(Constants.CREATE_ADMIN)
    public UserDto createAdminAccount(@RequestBody GetUserDto request){
            UserDto response=SpringBeanUtil.getBean(UserService.class).createAdminAccount(request);
            return response;
    }

    @GetMapping(Constants.GET_ALL_PROJECTS)
    public List<ProjectDto> getAllProject(){
        List<ProjectDto> response=SpringBeanUtil.getBean(ProjectService.class).getAllProject();
        return  response;

    }

    @GetMapping(Constants.GET_ALL_ACCOUNTS)
    public List<UserDto> getAllAccounts(){
        List<UserDto> response=SpringBeanUtil.getBean(UserService.class).getAllAccounts();
        return response;
    }


    @PatchMapping(  Constants.ADD_LEADER_TO_PROJECT+Constants.PROJECT_ID)
    public ResponseEntity<Object> addLeaderToProject(@RequestParam("projectId") String projectId,@RequestParam("leaderId") String leaderId){
        ProjectDto response=SpringBeanUtil.getBean(ProjectService.class).addLeaderToProject(projectId,leaderId);
        return  ResponseHandler.getResponse(response, HttpStatus.OK);
    }

    @GetMapping(Constants.GET_ALL_LEADER)
    public ResponseEntity<Object> getAllLeaders(){
        List<UserDto> response=SpringBeanUtil.getBean(UserService.class).getAllLeaders();
        if(response!=null)
            return  ResponseHandler.getResponse(response,HttpStatus.OK);
        return  ResponseHandler.getResponse("INTERNAL SERVER ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(Constants.FIND_USER)
    public ResponseEntity<Object> searchUser(@RequestParam("username") String username){
        List<UserDto> response=SpringBeanUtil.getBean(UserService.class).findByName(username);
        return ResponseHandler.getResponse(response,HttpStatus.OK);
    }



}
