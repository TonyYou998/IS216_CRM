package org.example.services;

import org.example.dto.*;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CallService {

    @POST("login")
    Call<MyResponse<LoginResponse>> loginResponseCall(@Body LoginRequest loginRequest);

    @POST("admin/account/create-admin")
    Call<MyResponse<LoginResponse>> postCreateAdmin (@Body LoginRequest loginRequest);

    @POST("admin/project/create")
    Call<MyResponse<CreateProjectResponse>> postCreateProject (@Body CreateProjectRequest createProjectRequest,@Header("Authorization") String token);

    @POST("admin/account/create-user")
    Call<CreateUserResponse> postCreateUser(@Body CreateUserRequest createUserRequest,@Header("Authorization") String token);

    @POST("user/task/leader/create")
    Call<MyResponse<LoginResponse>> postCreateTask (@Body CreateTaskRequest createTaskRequest,@Header("Authorization") String token);

    @GET("user/all-project")
    Call<MyResponse<List<GetAllProjectResponse>>> getAllProject(@Header("Authorization") String token);

    @GET("admin/projects/get-all-project")
    Call<List<GetAllProjectResponse>> getAllProjectForAdmin(@Header("Authorization") String token);

    @GET("admin/account/get-all")
    Call<List<GetAllUserAccountResponse>> getAllUser(@Header("Authorization") String token);

    @GET("user/task/project{id}")
    Call<MyResponse<List<GetTaskResponse>>> getTaskByProjectId(@Header("Authorization") String token,@Path("id") int id);

    @GET("admin/leaders")
    Call<MyResponse<List<GetLeaderResponse>>> getLeaders(@Header("Authorization") String token);

}
