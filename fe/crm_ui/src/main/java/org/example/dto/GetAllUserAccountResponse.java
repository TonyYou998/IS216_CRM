package org.example.dto;

import lombok.Data;

@Data
public class GetAllUserAccountResponse {
    private int id;
    private String username;
    private String roleId;
    private String roleName;
    private String phone;
    private String fullName;
    private String address;
    private String email;
    private String token;
}
