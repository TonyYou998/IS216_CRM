package org.example.ui;

import org.example.dto.GetProjectResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;

public class ProjectsScreen extends JDialog {

    private JPanel panel_projects_screen;

    public ProjectsScreen(JFrame parent, String token) {
        super(parent);

        CallApiGetAllProject(token);
        setTitle("ProjectScreen");
        setContentPane(panel_projects_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void CallApiGetAllProject(String token) {
        Call<MyResponse<GetProjectResponse>> getProjectResponseCall= ApiClient.callApi().getProjectResponseCall(token);
        getProjectResponseCall.enqueue(new Callback<MyResponse<GetProjectResponse>>() {
            @Override
            public void onResponse(Call<MyResponse<GetProjectResponse>> call, Response<MyResponse<GetProjectResponse>> response) {

            }

            @Override
            public void onFailure(Call<MyResponse<GetProjectResponse>> call, Throwable throwable) {

            }
        });
    }
}
