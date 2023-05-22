package com.uit.crm.task.repository;

import com.uit.crm.project.model.Project;
import com.uit.crm.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByProject(Project p);
}
