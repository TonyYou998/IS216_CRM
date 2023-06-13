package com.uit.crm.user.repository;

import com.uit.crm.role.model.Role;
import com.uit.crm.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findByEmail(String username);

    List<User> findAllByRole(Role r);

   @Query(value = "SELECT  u.* \n FROM users u  \n WHERE username LIKE CONCAT('%', :username, '%')  ",nativeQuery = true)
    List<User> findByUserName(String username);
}
