package com.uit.crm.project.controller;

import com.uit.crm.common.Constants;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_URL+Constants.REQUEST_MAPPING)
public class ProjectController {
        @PostMapping(Constants.CREATE_PROJECT)
        public ProjectDto createProject(@RequestBody ProjectDto request){
            ProjectDto response= SpringBeanUtil.getBean(ProjectService.class).createProject(request);
                return  response;
        }

}
