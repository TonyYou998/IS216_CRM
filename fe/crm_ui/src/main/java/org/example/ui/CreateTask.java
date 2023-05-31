package org.example.ui;

import org.example.dto.*;
import org.example.ui.components.ComboBoxItem;
import org.example.utils.ApiClient;
import org.jdesktop.swingx.JXDatePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CreateTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JComboBox cb_employee;
    private JButton btn_save;
    private JPanel panel_createtask;
    private JButton CREATEButton;
    private JButton CANCELButton;
    private JXDatePicker dp_date;
    private List<GetAllUserAccountResponse> lstEmployee;
    private String userId="-1";

    DateFormat dateFormat;
    String date;
    Date currentDate = new Date();
    public CreateTask(JFrame parent,String token) {
        super(parent);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dp_date.setFormats(dateFormat);
        dp_date.setDate(currentDate);

        setTitle("Create Task");
        setContentPane(panel_createtask);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);
        lstEmployee=TaskScreen.callApiGetEmployeeInProject(token,1);
        setEmployee(lstEmployee);
        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                date = dateFormat.format(dp_date.getDate());

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                LocalDateTime startDate=LocalDateTime.now();
                CreateTaskRequest createTaskRequest = new CreateTaskRequest(tf_taskname.getText(),startDate.toString(),date,userId,"1");
                callApiCreateTask(createTaskRequest,token);
            }
        });
        setVisible(true);



    }

    private void callApiCreateTask(CreateTaskRequest createTaskRequest,String token) {
        Call<MyResponse<CreateTaskResponse>> myResponseCall = ApiClient.callApi().postCreateTask(createTaskRequest,"Bearer "+token);
        myResponseCall.enqueue(new Callback<MyResponse<CreateTaskResponse>>() {
            @Override
            public void onResponse(Call<MyResponse<CreateTaskResponse>> call, Response<MyResponse<CreateTaskResponse>> response) {
                MyResponse<CreateTaskResponse> myResponse = response.body();
                if (myResponse.getStatus() == 200) {
                    System.out.println("call ok");

                }
            }

            @Override
            public void onFailure(Call<MyResponse<CreateTaskResponse>> call, Throwable throwable) {
                System.out.println("call fail");

            }
        });
    }
    public void setEmployee(List<GetAllUserAccountResponse> lstEmployee){

            cb_employee.addItem(new ComboBoxItem("none","-1"));
        for(GetAllUserAccountResponse item:lstEmployee){
            ComboBoxItem comboBoxItem=new ComboBoxItem(item.getUsername(),String.valueOf(item.getId()));
            cb_employee.addItem(comboBoxItem);
        }
        cb_employee.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                // Display only the username in the JComboBox
                return super.getListCellRendererComponent(list, ((ComboBoxItem) value).getDisplayText(), index,
                        isSelected, cellHasFocus);
            }
        });
        cb_employee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboBoxItem selectedLeader = (ComboBoxItem) cb_employee.getSelectedItem();
               userId  = selectedLeader.getId();

                // Use the leaderId and other values as needed
                System.out.println("Selected Leader ID: " + userId);
            }
        });


    }

}
