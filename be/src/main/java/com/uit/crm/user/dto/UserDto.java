package com.uit.crm.user.dto;

import lombok.Data;

@Data
public class UserDto  {
    private Integer id;
    private String username;
//    @JsonIgnoreProperties("password")
    //bug return password

    private String roleId;
    private String phone;
    private String fullName;
    private String address;
    private String email;
    private String token;

}
