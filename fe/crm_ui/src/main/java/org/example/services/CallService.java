package org.example.services;

import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.dto.MyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CallService {
    @POST("user/login")
    Call<MyResponse<LoginResponse>> loginResponseCall(@Body LoginRequest loginRequest);
}
