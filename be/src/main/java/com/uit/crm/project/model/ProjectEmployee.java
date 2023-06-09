package com.uit.crm.project.model;

import com.uit.crm.common.BaseEntity;
import com.uit.crm.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(uniqueConstraints =@UniqueConstraint(columnNames = {"project_id","user_id"}))
public class ProjectEmployee extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public ProjectEmployee() {

    }
//    private Task taskId;
}
