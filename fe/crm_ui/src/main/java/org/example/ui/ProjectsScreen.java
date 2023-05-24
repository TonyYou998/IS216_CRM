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
    private  List<GetAllProjectResponse> lstProject;

    public ProjectsScreen(JFrame parent, String token) throws IOException {
        super(parent);
        System.out.print("Bearer "+token);

        callApiPj(token);

        setTitle("ProjectScreen");
        setContentPane(panel_projects_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public void callApiPj(String token) {


    }

}
