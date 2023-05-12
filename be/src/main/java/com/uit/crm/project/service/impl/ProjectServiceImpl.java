package com.uit.crm.project.service.impl;

import com.uit.crm.common.utils.JwtUtils;
import com.uit.crm.common.utils.LoggerUtil;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.dto.ProjectEmployeeDto;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.model.ProjectEmployee;
import com.uit.crm.project.repository.ProjectEmployeeRepository;
import com.uit.crm.project.repository.ProjectRepository;
import com.uit.crm.project.service.ProjectService;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private ModelMapper mapper;

    @Override
    public ProjectDto createProject(ProjectDto request) {
        User leader= SpringBeanUtil.getBean(UserRepository.class).findById( Long.parseLong(request.getLeaderId())).orElse(null);
        ProjectDto response=null;
        try{
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
        catch (Exception e){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(ProjectServiceImpl.class).info(e.getMessage());
        }


    return response;
    }

    @Override
    public List<ProjectDto> getAllProject() {
        List<ProjectDto> responses=new ArrayList<>();
        List<Project> lstProject=SpringBeanUtil.getBean(ProjectRepository.class).findAll();
        for (Project project : lstProject) {
               ProjectDto dto=  mapper.map(project, ProjectDto.class);
               dto.setLeaderId(Long.toString(project.getProjectLeader().getId()));
               responses.add(dto);
        }
        return  responses;
    }

    @Override
    public ProjectEmployeeDto addEmployee(String userId, String projectId) {
        User u=SpringBeanUtil.getBean(UserRepository.class).findById(Long.parseLong(userId)).orElse(null);
        Project p=SpringBeanUtil.getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);

        if(u!=null&&p!=null){
            try{
                ProjectEmployee pE=new ProjectEmployee();
                pE.setUser(u);
                pE.setProject(p);
                SpringBeanUtil.getBean(ProjectEmployeeRepository.class).save(pE);
                ProjectEmployeeDto response= new ProjectEmployeeDto();
                response.setUserId(pE.getUser().getId().toString());
                response.setProjectId(pE.getProject().getId().toString());
                return response;
            }

            catch (DataIntegrityViolationException e){
//                unsuitable
                SpringBeanUtil.getBean(LoggerUtil.class).logger(ProjectServiceImpl.class).info(e.getMessage());
                ProjectEmployeeDto response=new ProjectEmployeeDto();
                response.setMessage("duplicated");
                return response;
            }


        }

        return null;
    }

    @Override
    public ProjectDto addLeaderToProject(String projectId, String leaderId) {
        try{
            User u=SpringBeanUtil.getBean(UserRepository.class).findById(Long.parseLong(leaderId)).orElse(null);
            Project p=SpringBeanUtil.getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);
            assert  u.getRole().getRoleName()!="LEADER":"roleName must be LEADER";
            if(u!=null && p!=null){
                p.setProjectLeader(u);
                return mapper.map(p,ProjectDto.class);
            }
        }
        catch (AssertionError e){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(ProjectServiceImpl.class).info(e.getMessage());
            return null;
        }
        return  null;
    }

    @Override
    public List<ProjectDto> findByUser(String request) {
        try {
            String token= SpringBeanUtil.getBean(JwtUtils.class).getTokenFromHeader(request);
            if(token==null)
                return null;
            String email= SpringBeanUtil.getBean(JwtUtils.class).getEmailFromToken(token);
            User u=SpringBeanUtil.getBean(UserRepository.class).findByEmail(email);
            List<ProjectDto> lstResponse=new LinkedList<>();
            assert u!=null:"userId is not exists";
            if(u!=null){
                List<Project> lstProject= SpringBeanUtil.getBean(ProjectRepository.class).findByUser(Integer.parseInt(u.getId().toString()));
                for(Project p:lstProject){
                    ProjectDto response=mapper.map(p,ProjectDto.class);
                    lstResponse.add(response);
                }

            }
            return lstResponse;
        }
        catch (AssertionError e){
            SpringBeanUtil.getBean(LoggerUtil.class).logger(ProjectService.class).info(e.getMessage());
            return null;
        }


    }
}
