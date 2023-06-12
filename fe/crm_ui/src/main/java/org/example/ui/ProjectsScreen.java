package org.example.ui;

import org.example.dto.GetAllProjectResponse;
import org.example.dto.MyResponse;
import org.example.ui.components.ComboBoxItem;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ProjectsScreen extends JDialog {

    private JPanel panel_projects_screen;
    private JButton OPENButton;
    private JComboBox cb_chooseProject;
    private JLabel lb_name;
    private JPanel mainPanel;

    private JPanel panel_listpj;
    private JPanel panel_card;
    private List<GetAllProjectResponse> lstProject;
    private int projectId=-1;
    private String token;


    public ProjectsScreen(JFrame parent, String token,String username) throws IOException {
        super(parent);

        this.token=token;

        lb_name.setText("       Welcome back! "+username);
        lstProject = callApiGetProjectByUser(token);
        setProject(lstProject);

        setTitle("Project Screen");
        setContentPane(panel_projects_screen);
        setMinimumSize(new Dimension(500,300));
        setModal(true);
        setLocationRelativeTo(null);

        OPENButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(projectId != -1) {
                    for (GetAllProjectResponse projectResponse : lstProject) {
                        if(projectId == projectResponse.getId()) {
                            try {
                                dispose();
                                new TaskScreen(null,token,projectResponse);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }

                }
            }
        });
        setVisible(true);
    }

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
                projectId  = Integer.parseInt(selectedProject.getId());

            }
        });


    }
}