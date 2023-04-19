package com.uit.crm.project.model;

import com.uit.crm.common.BaseEntity;
import com.uit.crm.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints =@UniqueConstraint(columnNames = {"project_id","user_id"}))
public class ProjectEmployee extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
//    private Task taskId;
}
