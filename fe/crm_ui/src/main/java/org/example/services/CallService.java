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
    Call<MyResponse<CreateUserResponse>> postCreateUser(@Body CreateUserRequest createUserRequest,@Header("Authorization") String token);

    @POST("user/task/leader/create")
    Call<MyResponse<CreateTaskResponse>> postCreateTask (@Body CreateTaskRequest createTaskRequest,@Header("Authorization") String token);

    @GET("user/all-project")
    Call<MyResponse<List<GetAllProjectResponse>>> getAllProject(@Header("Authorization") String token);

    @GET("admin/projects/get-all-project")
    Call<List<GetAllProjectResponse>> getAllProjectForAdmin(@Header("Authorization") String token);

    @GET("admin/account/get-all")
    Call<List<GetAllUserAccountResponse>> getAllUser(@Header("Authorization") String token);

    @GET("user/task/project")
    Call<MyResponse<List<GetTaskResponse>>> getTaskByProjectId(@Header("Authorization") String token,@Query("id") int id);

    @GET("admin/leaders")
    Call<MyResponse<List<GetLeaderResponse>>> getLeaders(@Header("Authorization") String token);

   @GET("user/leader/employees/projectId")
    Call<MyResponse<List<GetAllUserAccountResponse>>> getAllEmployeeInProject(@Header("Authorization") String token, @Query("id") int projectId);

   @GET("admin/project/add-employee")
   Call<MyResponse<AddEmployeeResponse>> getAddEmployee (@Header("Authorization") String token, @Query("userId") String userId,@Query("projectId") int projectId);

    @PATCH("user/task/update/taskId")
    Call<MyResponse<CreateTaskResponse>> patchUpdateTask (@Header("Authorization") String token, @Query("id") int id,@Body CreateTaskRequest createTaskRequest);
   @GET("user/all-project")
    Call<MyResponse<List<GetAllProjectResponse>>> getAllProjectByUser(@Header("Authorization") String token);
//    @GET("admin/leaders")
//    Call<MyResponse<List<GetTaskResponse>>> getTaskByProjectId(@Header("Authorization") String token,@Query("id") int id);
}
