package org.example.ui;

import org.example.dto.*;
import org.example.ui.components.ComboBoxItem;
import org.example.utils.ApiClient;
import org.jdesktop.swingx.JXDatePicker;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CreateNewProject extends JDialog {
    private JTextField tf_pjname;
    private JComboBox cb_leader;
    private JButton btn_create;
    private JButton btn_cancel;
    private JPanel panel_createnewpj;
    private JXDatePicker dp_start_date;
    private JXDatePicker dp_due_date;

    private String leaderId;

    DateFormat dateFormat;
    String startDate;
    String dueDate;
    Date currentDate = new Date();
    private List<GetLeaderResponse> lstLeader;
    public CreateNewProject(JFrame parent, String token) {
        super(parent);

        setTitle("Create new project");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        dp_start_date.setFormats(dateFormat);
        dp_start_date.setDate(currentDate);
        dp_due_date.setFormats(dateFormat);
        dp_due_date.setDate(currentDate);

        lstLeader = callApiGetAllLeader(token);
        setLeader(lstLeader);
        setContentPane(panel_createnewpj);
        setMinimumSize(new Dimension(550, 350));
        setModal(true);
        setLocationRelativeTo(null);

        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                startDate = dateFormat.format(dp_start_date.getDate());
                dueDate=dateFormat.format(dp_due_date.getDate());
                CreateProjectRequest createProjectRequest=new CreateProjectRequest(tf_pjname.getText(),startDate,dueDate,leaderId);
                CreateProjectResponse response= callApiCreateNewProject(createProjectRequest,token);
                if(response!=null)
                    JOptionPane.showMessageDialog(null, "Successfully created a project!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
            }
        });
        btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }


        public void setLeader(List<GetLeaderResponse> lstLeader){
            for(GetLeaderResponse item:lstLeader){
                ComboBoxItem comboBoxItem=new ComboBoxItem(item.getUsername(),item.getId());
               cb_leader.addItem(comboBoxItem);
            }
            cb_leader.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    // Display only the username in the JComboBox
                    return super.getListCellRendererComponent(list, ((ComboBoxItem) value).getDisplayText(), index,
                            isSelected, cellHasFocus);
                }
            });
            cb_leader.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ComboBoxItem selectedLeader = (ComboBoxItem) cb_leader.getSelectedItem();
                     leaderId = selectedLeader.getId();

                    // Use the leaderId and other values as needed
                    System.out.println("Selected Leader ID: " + leaderId);
                }
            });


        }

        public List<GetLeaderResponse> callApiGetAllLeader(String token){
            Call<MyResponse<List<GetLeaderResponse>>> call = ApiClient.callApi().getLeaders("Bearer "+token);
            try{
                Response<MyResponse<List<GetLeaderResponse>>> response= call.execute();
                if(response.isSuccessful()){

                    MyResponse<List<GetLeaderResponse>> lstLeaders= response.body();
                    return lstLeaders.getContent();
                }


            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        public CreateProjectResponse callApiCreateNewProject(CreateProjectRequest createProjectRequest,String token){
            Call<MyResponse<CreateProjectResponse>> call = ApiClient.callApi().postCreateProject(createProjectRequest,"Bearer "+token);

            try{
                Response<MyResponse<CreateProjectResponse>> response=call.execute();
                if(response.isSuccessful()){
                    MyResponse<CreateProjectResponse> responseBody=response.body();
                    return responseBody.getContent();
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
            return null;
        }


}
