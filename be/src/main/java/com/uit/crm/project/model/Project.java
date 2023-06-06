package com.uit.crm.project.model;

import com.uit.crm.common.BaseEntity;
import com.uit.crm.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
public class Project extends BaseEntity {
    @Column(name="project_name")
    private String projectName;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name="leader_id")
    private User projectLeader;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private Set<ProjectEmployee> lstProjectEmployee=new HashSet<>();


}
