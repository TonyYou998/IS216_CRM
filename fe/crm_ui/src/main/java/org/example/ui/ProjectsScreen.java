package org.example.ui;

import org.example.dto.GetAllProjectResponse;
import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.MyResponse;
import org.example.ui.components.ComboBoxItem;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ProjectsScreen extends JDialog {

    private JPanel panel_projects_screen;
    private JComboBox cb_chooseProject;
    private JButton openButton;
    private JPanel mainPanel;

    private JPanel panel_listpj;
    private JPanel panel_card;
    private List<GetAllProjectResponse> lstProject;
    private String projectId="-1";
    private String token;


    public ProjectsScreen(JFrame parent, String token) throws IOException {
        super(parent);
        setTitle("Project Screen");
        setContentPane(panel_projects_screen);
        this.token=token;
        setMinimumSize(new Dimension(800,500));
        lstProject = callApiGetProjectByUser(token);
        setProject(lstProject);
        setVisible(true);
    }

    // public void callApiPj(String token) {
    //     Call<MyResponse<List<GetAllProjectResponse>>> responseCall = ApiClient.callApi().getAllProject(token);
    //     responseCall.enqueue(new Callback<MyResponse<List<GetAllProjectResponse>>>() {
    //         @Override
    //         public void onResponse(Call<MyResponse<List<GetAllProjectResponse>>> call, Response<MyResponse<List<GetAllProjectResponse>>> response) {
    //             if(response.isSuccessful()) {
    //                 MyResponse<List<GetAllProjectResponse>> listMyResponse = response.body();
    //                 listProject = listMyResponse.getContent();
    //                 if (listProject == null) {
    //                     System.out.print("null");
    //                 } else {
    //                     System.out.print(listProject.size());
    //                 }
    //             }
    public List<GetAllProjectResponse> callApiGetProjectByUser(String token) {
        Call<MyResponse<List<GetAllProjectResponse>>> call = ApiClient.callApi().getAllProjectByUser("Bearer " + token);
        try {
            Response<MyResponse<List<GetAllProjectResponse>>> response = call.execute();
            if (response.isSuccessful()) {
                MyResponse<List<GetAllProjectResponse>> lstProject = response.body();
                return lstProject.getContent();
            }

        } catch (IOException e) {

        }
        return null;
    }

    public void setProject(List<GetAllProjectResponse> lstProject) {
        cb_chooseProject.addItem(new ComboBoxItem("None","-1"));
        for (GetAllProjectResponse item : lstProject) {
            ComboBoxItem comboBoxItem = new ComboBoxItem(item.getProjectName(), String.valueOf(item.getId()));
            cb_chooseProject.addItem(comboBoxItem);

        }
        cb_chooseProject.setRenderer(new DefaultListCellRenderer() {
                                         @Override
                                         public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                                       boolean isSelected, boolean cellHasFocus) {
                                             // Display only the username in the JComboBox
                                             return super.getListCellRendererComponent(list, ((ComboBoxItem) value).getDisplayText(), index,
                                                     isSelected, cellHasFocus);
                                         }
                                     }
        );

        cb_chooseProject.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComboBoxItem selectedProject = (ComboBoxItem) cb_chooseProject.getSelectedItem();
                projectId  = selectedProject.getId();

                // Use the leaderId and other values as needed
                System.out.println("Selected Leader ID: " + projectId);

                try {
                    dispose();
                    new TaskScreen(null,token,Integer.parseInt(projectId));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }
}