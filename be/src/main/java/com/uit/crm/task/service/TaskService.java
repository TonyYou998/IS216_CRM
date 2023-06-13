package com.uit.crm.task.service;

import com.uit.crm.task.dto.TaskDto;
import com.uit.crm.task.model.Task;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto request);

    TaskDto assignTask(TaskDto request);

    List<TaskDto> getTaskByProjectId(String projectId, String authHeader);

    List<TaskDto> getTaskByUserId(String authHeader);


    TaskDto updateTask(String taskId, String authHeader, TaskDto request);

    List<TaskDto> getAllBackLog(String id, String authHeader);

    List<TaskDto> getAllInProgress(String id, String authHeader);

    List<TaskDto> getTasksDone(String projectId, String authHeader);

    List<TaskDto> getMyTasks(String projectId, String authHeader);

    List<Task> getOverDueTask();
}
