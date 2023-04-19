package com.uit.crm.project.repository;

import com.uit.crm.project.model.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee,Long> {
}
