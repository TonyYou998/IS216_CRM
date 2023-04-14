package com.uit.crm.role.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uit.crm.common.BaseEntity;
import com.uit.crm.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Role extends BaseEntity {
    @Column(name="role_name")
    private  String roleName="";

    @OneToMany(mappedBy = "id")
    @JsonIgnore
    Set<User> lstUser = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {

    }
}
