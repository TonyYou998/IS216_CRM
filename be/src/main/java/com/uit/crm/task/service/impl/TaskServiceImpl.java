package com.uit.crm.task.service.impl;

import com.uit.crm.common.utils.JwtUtils;
import com.uit.crm.common.utils.LoggerUtil;
import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.repository.ProjectRepository;
import com.uit.crm.task.dto.TaskDto;
import com.uit.crm.task.model.Task;
import com.uit.crm.task.model.repository.TaskRepository;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.uit.crm.common.utils.SpringBeanUtil.getBean;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private ModelMapper mapper;
    @Transactional
    @Override
    public TaskDto createTask(TaskDto request) {
        Task t=new Task();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User u= getBean(UserRepository.class).findById(Long.parseLong(request.getAssigneeEmployeeId())).orElse(null);
        Project p= getBean(ProjectRepository.class).findById(Long.parseLong(request.getProjectId())).orElse(null);
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
            t.setDescription(request.getDescription());
            getBean(TaskRepository.class).save(t);

            respones= mapper.map(t,TaskDto.class);
            if(t.getAssignedEmployeeId()!=null)
                respones.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
            respones.setProjectId(t.getProject().getId().toString());
            return respones;



        }
        return null;



    }

    @Override
    public TaskDto assignTask(TaskDto request) {
            Task task= getBean(TaskRepository.class).findById(request.getId()).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User u= getBean(UserRepository.class).findById(Long.parseLong(request.getAssigneeEmployeeId())).orElse(null);
            if(task==null || u==null)
                return null;
            task.setAssignedEmployeeId(u);
            getBean(TaskRepository.class).save(task);
        TaskDto response= mapper.map(task,TaskDto.class);
        response.setAssigneeEmployeeId(u.getId().toString());
        return response;
    }

    @Override
    public List<TaskDto> getTaskByProjectId(String projectId, String authHeader) {
        try {
            String token= getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String email= getBean(JwtUtils.class).getEmailFromToken(token);
            Long userId= getBean(UserRepository.class).findByEmail(email).getId();
            Project p= getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);
//            assert userId!=null:"UserId not found";
//            assert p!=null:"Project not found";
            Assert.notNull(userId,"userId not found");
            Assert.notNull(p,"project not found");

            List<Task>lstTask= getBean(TaskRepository.class).findAllByProject(p);
            List<TaskDto> lstDto=new LinkedList<>();
            for(Task item:lstTask){
                TaskDto dto=mapper.map(item,TaskDto.class);

                dto.setProjectId(item.getProject().getId().toString());
                if(item.getAssignedEmployeeId()!=null){
                    dto.setAssigneeEmployeeId(item.getAssignedEmployeeId().getId().toString());
                    dto.setAssignEmployeeName(item.getAssignedEmployeeId().getUsername());
                }


                lstDto.add(dto);
            }
            return lstDto;

        }
        catch (IllegalArgumentException ex){
           throw new IllegalArgumentException(ex.getMessage());

        }


    }

    @Override
    public List<TaskDto> getTaskByUserId(String authHeader) {
        try {
            String token= getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String username= getBean(JwtUtils.class).getEmailFromToken(token);
            User u= getBean(UserRepository.class).findByEmail(username);
            Assert.notNull(u,"User not exist");
            List<Task> lstTask= getBean(TaskRepository.class).findAllByAssignedEmployeeId(u);
            List<TaskDto> lstDto=new LinkedList<>();
            if(lstTask.size()>0){
                TaskDto dto;
                for(Task item:lstTask){
                    dto=mapper.map(item,TaskDto.class);
                    dto.setAssignEmployeeName(item.getAssignedEmployeeId().getUsername());
                    dto.setAssigneeEmployeeId(item.getAssignedEmployeeId().getId().toString());
                    lstDto.add(dto);
                }
                return lstDto;
            }
            else
                return lstDto;
        }
        catch (Exception e){
            getBean(LoggerUtil.class).logger(TaskServiceImpl.class).info(e.getMessage());

        }
        return null;
    }

    @Override
    public TaskDto updateTask(String taskId, String authHeader,TaskDto request) {
        try {
            Task t= getBean(TaskRepository.class).findById(Long.parseLong(taskId)).orElse(null);
            Assert.notNull(t,"Task not exist");
            String token= getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String email= getBean(JwtUtils.class).getEmailFromToken(token);
            User u= getBean(UserRepository.class).findByEmail(email);
//            Assert.notNull(u,"User is not exist");

                User user= getBean(UserRepository.class).findById(Long.parseLong(request.getAssigneeEmployeeId())).orElse(null);
//                Assert.notNull(user,"User not exist");
                t.setAssignedEmployeeId(user);



                t.setTaskName(request.getTaskName());
                t.setStartDate(request.getStartDate());
                t.setEndDate(request.getEndDate());
                t.setStatus(request.getStatus());

                getBean(TaskRepository.class).save(t);
                TaskDto dto= mapper.map(t,TaskDto.class);
                dto.setProjectId(t.getProject().getId().toString());
                dto.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
                dto.setAssignEmployeeName(t.getAssignedEmployeeId().getUsername());
                return dto;

        }
        catch (Exception ex){
            getBean(LoggerUtil.class).logger(TaskServiceImpl.class).info(ex.getMessage());


        }

        return null;
    }

    @Override
    public List<TaskDto> getAllBackLog(String id, String authHeader) {
        Project p= getBean(ProjectRepository.class).findById(Long.parseLong(id)).orElse(null);

       List<Task> lstTask= getBean(TaskRepository.class).findByProjectAndStatus(p,"NOT START");
       List<TaskDto> lstDto=new LinkedList<>();
       for(Task t:lstTask){
           TaskDto dto=mapper.map(t,TaskDto.class);
           if(t.getAssignedEmployeeId()!=null){
               dto.setAssignEmployeeName(t.getAssignedEmployeeId().getUsername());
               dto.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
           }

           dto.setProjectId(t.getProject().getId().toString());
           lstDto.add(dto);

       }



        return lstDto;
    }

    @Override
    public List<TaskDto> getAllInProgress(String id, String authHeader) {
        Project p= getBean(ProjectRepository.class).findById(Long.parseLong(id)).orElse(null);

        List<Task> lstTask= getBean(TaskRepository.class).findByProjectAndStatus(p,"IN-PROGRESS");

        List<TaskDto> lstDto=new LinkedList<>();

        for(Task t:lstTask){
            TaskDto dto=mapper.map(t,TaskDto.class);
            dto.setAssignEmployeeName(t.getAssignedEmployeeId().getUsername());
            dto.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
            dto.setProjectId(t.getProject().getId().toString());
            lstDto.add(dto);
        }



        return lstDto;
    }

    @Override
    public List<TaskDto> getTasksDone(String projectId, String authHeader) {

        try{
            Project p= getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);
            List<Task> lstTask= getBean(TaskRepository.class).findByProjectAndStatus(p,"DONE");
            List<TaskDto> lstDto=new LinkedList<>();
            if(lstTask.size()==0){
                return lstDto;
            }


            for(Task t:lstTask){
                TaskDto dto=mapper.map(t,TaskDto.class);
                if(t.getAssignedEmployeeId()!=null){
                    dto.setAssignEmployeeName(t.getAssignedEmployeeId().getUsername());
                    dto.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
                }
                dto.setProjectId(t.getProject().getId().toString());
                lstDto.add(dto);
            }



            return lstDto;

        }
        catch (Exception ex){

        }
        return null;
    }

    @Override
    public List<TaskDto> getMyTasks(String projectId, String authHeader) {
        try{
            Project p= getBean(ProjectRepository.class).findById(Long.parseLong(projectId)).orElse(null);
            String token= getBean(JwtUtils.class).getTokenFromHeader(authHeader);
            String username= getBean(JwtUtils.class).getUsernameFromToken(token);
            User u= getBean(UserRepository.class).findByUsername(username);
            Assert.notNull(u,"User not found");
            List<Task> lstTask= getBean(TaskRepository.class).findByProjectAndAssignedEmployeeId(p,u);
            List<TaskDto> lstDto=new LinkedList<>();
            if(lstTask.size()>0){
                for(Task t:lstTask){
                    TaskDto dto=mapper.map(t,TaskDto.class);
                    if(t.getAssignedEmployeeId()!=null){
                        dto.setAssignEmployeeName(t.getAssignedEmployeeId().getUsername());
                        dto.setAssigneeEmployeeId(t.getAssignedEmployeeId().getId().toString());
                    }
                    dto.setProjectId(t.getProject().getId().toString());
                    lstDto.add(dto);
                }
                return lstDto;
            }
        }
        catch (Exception ex){
            getBean(LoggerUtil.class).logger(TaskServiceImpl.class).info(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Task> getOverDueTask() {
        try{
            LocalDateTime currentDateTime=LocalDateTime.now();
            List<Task>overdueTasks=  SpringBeanUtil.getBean(TaskRepository.class).findOverdueTasks();
            if(!overdueTasks.isEmpty()){
                return overdueTasks;
            }
            return null;
        }
        catch (Exception ex){

        }
        return null;
    }
}
