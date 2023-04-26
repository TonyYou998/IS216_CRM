package com.uit.crm.project.service;

import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.dto.ProjectEmployeeDto;
import com.uit.crm.user.dto.UserDto;

import java.util.List;

public interface ProjectService {

    ProjectDto createProject(ProjectDto request);

    List<ProjectDto> getAllProject();

    ProjectEmployeeDto addEmployee(String userId, String projectId);

    ProjectDto addLeaderToProject(String projectId, String leaderId);
}
