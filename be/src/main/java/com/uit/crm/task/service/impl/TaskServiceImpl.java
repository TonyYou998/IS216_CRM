package com.uit.crm.task.service.impl;

import com.uit.crm.common.utils.SpringBeanUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
