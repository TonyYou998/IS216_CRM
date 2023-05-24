package com.uit.crm.user.service;

import com.uit.crm.user.dto.GetUserDto;
import com.uit.crm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(GetUserDto dto);

    UserDto createAdminAccount(GetUserDto request);

    List<UserDto> getAllAccounts();

    List<UserDto> getAllLeaders();
}
