package com.uit.crm.project.repository;

import com.uit.crm.project.model.Project;
import com.uit.crm.project.model.ProjectEmployee;
import com.uit.crm.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee,Long> {
    List<ProjectEmployee> findByProject(Project p);

    List<ProjectEmployee> deleteByUser(User u);

    List<ProjectEmployee> findByUser(User u);
}
