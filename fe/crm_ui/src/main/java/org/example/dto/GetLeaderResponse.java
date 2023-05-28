package org.example.dto;

import lombok.Data;

@Data
public class GetLeaderResponse {
    String id;
    String username;
    String fullName;
    String email;
}
