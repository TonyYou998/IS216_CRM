package com.uit.crm.user.service;

import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(GetUserDto dto);

    UserDto createAdminAccount(GetUserDto request);

    List<UserDto> getAllAccounts();

    List<UserDto> getAllLeaders();

    List<UserDto> getAllEmployee(String token);

    List<UserDto> getAllEmployeeInProject(String projectId, String id);

    List<UserDto> findByName(String username);
    void sendEmailToUser(String userId,String taskName );


   UserDto deleteUser(String userId);
}
