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
    @ManyToOne(cascade = CascadeType.ALL)
    private User assignedEmployeeId;
    private boolean status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="project_id")
    private Project project;
}
