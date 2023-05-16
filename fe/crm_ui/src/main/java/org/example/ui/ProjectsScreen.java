package org.example.ui;

import org.example.dto.GetProjectResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ProjectsScreen extends JDialog {

    private JPanel panel_projects_screen;
    private  GetProjectResponse[] lstProject;

    public ProjectsScreen(JFrame parent, String token) throws IOException {
        super(parent);
//        list project get from backend
        lstProject= CallApiGetAllProject(token);
        setTitle("ProjectScreen");
        setContentPane(panel_projects_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private GetProjectResponse[] CallApiGetAllProject(String token) throws IOException {
        Call<MyResponse<GetProjectResponse[]>> getProjectResponseCall= ApiClient.callApi().getProjectResponseCall("Bearer "+ token);
        Response<MyResponse<GetProjectResponse[]>> response=getProjectResponseCall.execute();
        if(response.isSuccessful()){
            MyResponse<GetProjectResponse[]> myResponse=response.body();
                return myResponse.getContent();

        }
        else {
            System.out.println("call failed");
            return new GetProjectResponse[]{};
        }
//        getProjectResponseCall.enqueue(new Callback<MyResponse<GetProjectResponse[]>>() {
//            @Override
//            public void onResponse(Call<MyResponse<GetProjectResponse[]>> call, Response<MyResponse<GetProjectResponse[]>> response) {
//                System.out.println("success");
//                System.out.println(response.body().getContent());
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse<GetProjectResponse[]>> call, Throwable throwable) {
//                System.out.println("failed");
//                System.out.println(throwable.toString());
//            }
//        });
    }
}
