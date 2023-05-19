package org.example.services;

import org.example.dto.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface CallService {
    @POST("login")
    Call<MyResponse<LoginResponse>> loginResponseCall(@Body LoginRequest loginRequest);

    @POST("admin/project/create")
//    Call<MyResponse<LoginResponse>> loginResponseCall(@Body CreateProjectRequest createProjectRequest);

    @GET("user/all-project")
    Call<MyResponse<GetAllProjectResponse[]>> getAllProject(@Header("Authorization") String token);

    @GET("admin/projects/get-all-project")
    Call<MyResponse<GetAllProjectResponse[]>> getAllProjectForAdmin(@Header("Authorization") String token);


}
