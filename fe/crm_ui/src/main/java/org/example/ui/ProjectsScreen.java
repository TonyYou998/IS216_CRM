package org.example.ui;

import org.example.dto.GetAllProjectResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ProjectsScreen extends JDialog {

    private JPanel panel_projects_screen;
    private JPanel mainPanel;

    private JButton button1;
    private JButton button2;
    private JPanel panel_listpj;
    private JPanel panel_card;
    private  List<GetAllProjectResponse> listProject;

    public ProjectsScreen(JFrame parent, String token) throws IOException {
        super(parent);

        callApiPj(token);

        setVisible(true);


    }

    public void callApiPj(String token) {
        Call<MyResponse<List<GetAllProjectResponse>>> responseCall = ApiClient.callApi().getAllProject(token);
        responseCall.enqueue(new Callback<MyResponse<List<GetAllProjectResponse>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<GetAllProjectResponse>>> call, Response<MyResponse<List<GetAllProjectResponse>>> response) {
                if(response.isSuccessful()) {
                    MyResponse<List<GetAllProjectResponse>> listMyResponse = response.body();
                    listProject = listMyResponse.getContent();
                    if (listProject == null) {
                        System.out.print("null");
                    } else {
                        System.out.print(listProject.size());
                    }

                }

            }

            @Override
            public void onFailure(Call<MyResponse<List<GetAllProjectResponse>>> call, Throwable throwable) {
                System.out.print("call failure user");

            }
        });

    }

}
