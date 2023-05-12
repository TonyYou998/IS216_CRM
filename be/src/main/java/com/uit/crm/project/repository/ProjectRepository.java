package com.uit.crm.project.repository;

import com.uit.crm.project.model.Project;
import com.uit.crm.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {


    @Query(value = "SELECT p.*, NULL as user_id " +
            "FROM project p " +
            "WHERE p.leader_id = :uid " +
            "UNION " +
            "SELECT p.*, pe.user_id " +
            "FROM project_employee pe " +
            "INNER JOIN project p ON p.id = pe.project_id " +
            "WHERE pe.user_id = :uid", nativeQuery = true)
    List<Project> findByUser(@Param("uid") Integer uid);



}
