package com.uit.crm.user.repository;

import com.uit.crm.role.model.Role;
import com.uit.crm.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findByEmail(String username);

    List<User> findAllByRole(Role r);
}
