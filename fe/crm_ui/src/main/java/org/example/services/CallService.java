package org.example.services;

import org.example.dto.GetProjectResponse;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.dto.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface CallService {
    @POST("login")
    Call<MyResponse<LoginResponse>> loginResponseCall(@Body LoginRequest loginRequest);

    @GET("user/all-project")
    Call<MyResponse<GetProjectResponse>> getProjectResponseCall(@Header("Authorization") String getAllProjectRequest);
}
