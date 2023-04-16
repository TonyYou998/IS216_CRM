package com.uit.crm.user.dto;

import com.uit.crm.role.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String roleId;
    private String phone;
    private String fullName;
    private String address;
    private String email;
    private String token;

}
