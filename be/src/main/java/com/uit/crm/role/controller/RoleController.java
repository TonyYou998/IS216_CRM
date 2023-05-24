package com.uit.crm.role.controller;


import com.uit.crm.common.Constants;
import com.uit.crm.common.helper.ResponseHandler;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.role.dto.RoleDto;
import com.uit.crm.role.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING)
public class RoleController {
        @GetMapping(Constants.GET_ALL_ROLES)
        public ResponseEntity<Object> getAllRoles(){
            List<RoleDto> response=SpringBeanUtil.getBean(RoleService.class).getAllRoles();
            if(response!=null)
                return ResponseHandler.getResponse(response, HttpStatus.OK);
            return ResponseHandler.getResponse("INTERNAL SERVER ERROR",HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
