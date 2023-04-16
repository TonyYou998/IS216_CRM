package com.uit.crm.user.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.service.UserService;

import com.uit.crm.common.utils.SpringBeanUtil;
import org.springframework.web.bind.annotation.*;

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


}
