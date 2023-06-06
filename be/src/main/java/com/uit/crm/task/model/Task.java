package com.uit.crm.task.model;

import com.uit.crm.common.BaseEntity;
import com.uit.crm.user.model.User;
import com.uit.crm.project.model.Project;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task extends BaseEntity {
    private String taskName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name="assignee_id",nullable = true)
    private User assignedEmployeeId;
    private String status="NOT START";
    @ManyToOne
    @JoinColumn(name="project_id",nullable = false)
    private Project project;
    private String description;
}
