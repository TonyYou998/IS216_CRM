package com.uit.crm.project.service.impl;

import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.repository.ProjectRepository;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.dto.UserDto;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private ModelMapper mapper;

    @Override
    public ProjectDto createProject(ProjectDto request) {
        User leader= SpringBeanUtil.getBean(UserRepository.class).findById( Long.parseLong(request.getLeaderId())).orElse(null);
        ProjectDto response=null;
        if(leader!=null && leader.getRole().getId()==2) {
            Project p = new Project();
            p.setProjectLeader(leader);
            p.setProjectName(request.getProjectName());
            p.setStartDate(request.getStartDate());
            p.setEndDate(request.getEndDate());
            SpringBeanUtil.getBean(ProjectRepository.class).save(p);
            response = mapper.map(p, ProjectDto.class);
            response.setLeaderId(leader.getId().toString());
        }

        return  response;
    }
}
