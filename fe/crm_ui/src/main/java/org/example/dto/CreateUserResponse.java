package org.example.dto;

import lombok.Data;

@Data
public class CreateUserResponse {
    String id;
    String username;
    String roleId;
    String phone;
    String fullName;
    String address;
    String email;
    String token;
}
