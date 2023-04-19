package org.example.dto;

import lombok.Data;

@Data
public class MyResponse<T> {
    private T content;
    private String status;
}
