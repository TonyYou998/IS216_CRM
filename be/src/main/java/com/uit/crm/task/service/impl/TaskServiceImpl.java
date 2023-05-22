package com.uit.crm.task.service.impl;

import com.uit.crm.common.utils.JwtUtils;
import com.uit.crm.common.utils.LoggerUtil;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.repository.ProjectRepository;
import com.uit.crm.task.dto.TaskDto;
import com.uit.crm.task.model.Task;
import com.uit.crm.task.repository.TaskRepository;
import com.uit.crm.task.service.TaskService;
import com.uit.crm.user.model.User;
import com.uit.crm.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private ModelMapper mapper;
    @Transactional
    @Override
    public TaskDto createTask(TaskDto request) {
        Task t=new Task();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User u= SpringBeanUtil.getBean(UserRepository.class).findById(Long.parseLong(request.getAssigneeEmployeeId())).orElse(null);
        Project p=SpringBeanUtil.getBean(ProjectRepository.class).findById(Long.parseLong(request.getProjectId())).orElse(null);
        TaskDto respones;
        if( p==null){
            return null;
        }
        if(request!=null){

            t.setTaskName(request.getTaskName());
            t.setStartDate(request.getStartDate());
            t.setEndDate(request.getEndDate());
            t.setAssignedEmployeeId(u);
            t.setProject(p);
            SpringBeanUtil.getBean(TaskRepository.class).save(t);

            respones= mapper.map(t,TaskDto.class);
            respones.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
            respones.setProjectId(t.getProject().getId().toString());
            return respones;



        }
        return null;
    }

    @Override
    public TaskDto assignTask(TaskDto request) {
            Task task=SpringBeanUtil.getBean(TaskRepository.class).findById(request.getId()).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User u=SpringBeanUtil.getBean(UserRepository.class).findById(Long.parseLong(request.getAssigneeEmployeeId())).orElse(null);
            if(task==null || u==null)
                return null;
            task.setAssignedEmployeeId(u);
            SpringBeanUtil.getBean(TaskRepository.class).save(task);
        TaskDto response= mapper.map(task,TaskDto.class);
        response.setAssigneeEmployeeId(u.getId().toString());
        return response;
    }

    @Override
    public List<TaskDto> getTaskByProjectId(String projectId, String authHeader) {
        try {
            String token=SpringBeanUtil.getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String email=SpringBeanUtil.getBean(JwtUtils.class).getEmailFromToken(token);
            Long userId= SpringBeanUtil.getBean(UserRepository.class).findByEmail(email).getId();
            Project p= SpringBeanUtil.getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);
//            assert userId!=null:"UserId not found";
//            assert p!=null:"Project not found";
            Assert.notNull(userId,"userId not found");
            Assert.notNull(p,"project not found");

            List<Task>lstTask= SpringBeanUtil.getBean(TaskRepository.class).findAllByProject(p);
            List<TaskDto> lstDto=new LinkedList<>();
            for(Task item:lstTask){
                TaskDto dto=mapper.map(item,TaskDto.class);

                dto.setProjectId(item.getProject().getId().toString());
                dto.setAssigneeEmployeeId(item.getAssignedEmployeeId().getId().toString());
                lstDto.add(dto);
            }
            return lstDto;

        }
        catch (IllegalArgumentException ex){
           throw new IllegalArgumentException(ex.getMessage());

        }


    }
}
