package org.example.services;

import org.example.dto.*;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CallService {
    @POST("login")
    Call<MyResponse<LoginResponse>> loginResponseCall(@Body LoginRequest loginRequest);

    @POST("admin/account/create-user")
    Call<CreateUserResponse> postCreateUser(@Body CreateUserRequest createUserRequest,@Header("Authorization") String token);

    @GET("user/all-project")
//    Call<MyResponse<GetAllProjectResponse[]>> getAllProject(@Header("Authorization") String token);
    Call<MyResponse<List<GetAllProjectResponse>>> getAllProject(@Header("Authorization") String token);

    @GET("admin/projects/get-all-project")
    Call<List<GetAllProjectResponse>> getAllProjectForAdmin(@Header("Authorization") String token);

    @GET("admin/account/get-all")
    Call<List<GetAllUserAccountResponse>> getAllUser(@Header("Authorization") String token);


}
