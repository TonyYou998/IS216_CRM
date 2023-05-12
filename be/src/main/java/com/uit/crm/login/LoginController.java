package com.uit.crm.login;

import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(Constants.BASE_URL+Constants.LOGIN)
@RestController
public class LoginController {
    @PostMapping
    public ResponseEntity<Object> login(@RequestBody GetUserDto request){
        UserDto response= SpringBeanUtil.getBean(UserServiceImpl.class).login(request);
        return ResponseHandler.getResponse(response, HttpStatus.OK);

    }
}
