package com.uit.crm.project.repository;

import com.uit.crm.project.model.Project;
import com.uit.crm.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {


    @Query(value = "SELECT p.* " +
            "FROM project p " +
            "JOIN project_employee pe ON p.id = pe.project_id " +
            "JOIN users u ON pe.user_id = u.id " +
            "WHERE u.id = :uid", nativeQuery = true)
    List<Project> findByUser(@Param("uid") Integer uid);



}
