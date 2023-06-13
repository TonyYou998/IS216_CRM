package com.uit.crm.project.service;

import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.project.dto.ProjectEmployeeDto;

import java.util.List;

public interface ProjectService {

    ProjectDto createProject(ProjectDto request);

    List<ProjectDto> getAllProject();

    ProjectEmployeeDto addEmployee(String userId, String projectId);

    ProjectDto addLeaderToProject(String projectId, String leaderId);

    List<ProjectDto> findByUser(String request);

    ProjectDto deleteProject(String id);

    List<ProjectDto> findProjectByName(String projectName);

    ProjectDto editProject(ProjectDto request, String projectId);
}
