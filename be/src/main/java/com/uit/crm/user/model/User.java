package com.uit.crm.user.model;

import com.uit.crm.common.BaseEntity;
import com.uit.crm.project.model.Project;
import com.uit.crm.project.model.ProjectEmployee;
import com.uit.crm.role.model.Role;
import com.uit.crm.task.model.Task;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {
    @Column(name = "username",unique = true)
    private String username;

    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "role_id")
    private Role role;

    private String phone;


    private String fullName;

    private String address;

    @Column(name="emai",unique = true)
    private String email;

    @OneToMany(mappedBy = "id")
    private Set<Task> lstTask=new HashSet<>();
    @OneToMany(mappedBy = "id")
    Set<Project> lstProject=new HashSet<>();

    @OneToMany(mappedBy = "id")
    private Set<ProjectEmployee> lstProjectEmployee=new HashSet<>();
}
