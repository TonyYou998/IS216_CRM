package org.example.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MyResponse<T> {
//    @SerializedName("content")
    private T content;
//    @SerializedName("content")
    private String status;
}
