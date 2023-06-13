package com.uit.crm.task.Scheduler;

import com.uit.crm.common.utils.SpringBeanUtil;
import com.uit.crm.task.model.Task;
import com.uit.crm.task.service.TaskService;
import com.uit.crm.user.model.User;
import com.uit.crm.user.service.UserService;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OverdueTaskScheduler {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Scheduled(cron = "0 50 22 * * *")
    public void checkOverdueTaskAndSendEmail(){
        List<Task> overdueTasks=taskService.getOverDueTask();
        if(overdueTasks.isEmpty())
            return;
            for(Task t:overdueTasks){
                String email=t.getAssignedEmployeeId().getEmail();
                userService.sendEmailToUser(email,t.getTaskName());
            }

    }
}
