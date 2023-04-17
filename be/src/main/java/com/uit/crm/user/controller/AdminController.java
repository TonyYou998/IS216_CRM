package com.uit.crm.user.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.service.UserService;

import com.uit.crm.common.utils.SpringBeanUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING)
public class AdminController {

    @PostMapping(Constants.CREATE_USER)
    public UserDto createUserAccount(@RequestBody UserDto request){

            UserDto response= SpringBeanUtil.getBean(UserService.class).createUser(request);
            return response;

    }
    @PostMapping(Constants.CREATE_ADMIN)
    public UserDto createAdminAccount(@RequestBody UserDto request){
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


}
