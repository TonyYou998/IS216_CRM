package com.uit.crm.user.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING_USER)
public class UserController {

    @PostMapping(Constants.LOGIN)
    public UserDto login(@RequestBody UserDto request){
        UserDto response= SpringBeanUtil.getBean(UserServiceImpl.class).login(request);
        return response;
    }

}
