package com.uit.crm.role.repository;

import com.uit.crm.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {


        Role findByRoleName(String employee);
}
