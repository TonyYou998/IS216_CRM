package org.example.dto;

import lombok.Data;

@Data
public class GetLeaderResponse {
    String id;
    String username;
    String roleId;
    String phone;
    String fullName;
    String address;
    String email;
    String token;
}
