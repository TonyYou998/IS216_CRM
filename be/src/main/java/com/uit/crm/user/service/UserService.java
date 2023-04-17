package com.uit.crm.user.service;

import com.uit.crm.project.dto.ProjectDto;
import com.uit.crm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto dto);

    UserDto createAdminAccount(UserDto request);

    List<UserDto> getAllAccounts();
}
