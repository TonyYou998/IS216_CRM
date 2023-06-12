package com.uit.crm.task.model.repository;

import com.uit.crm.project.model.Project;
import com.uit.crm.task.model.Task;
import com.uit.crm.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByProject(Project p);

    List<Task> findAllByAssignedEmployeeId(User u);

    List<Task> findByProjectAndStatus(Project p, String status);

    List<Task> findByProjectAndAssignedEmployeeId(Project p, User u);
}
