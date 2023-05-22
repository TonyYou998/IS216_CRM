package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    String username;
    String password;
    String phone;
    String fullName;
    String address;
    String email;
    String dateOfBirth;
    String roleId;
}
