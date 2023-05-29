package org.example.ui;

import org.example.dto.CreateTaskRequest;
import org.example.dto.CreateTaskResponse;
import org.example.dto.MyResponse;
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
import java.util.Date;

public class CreateTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JComboBox cb_employee;
    private JButton btn_save;
    private JPanel panel_createtask;
    private JButton CREATEButton;
    private JButton CANCELButton;
    private JXDatePicker dp_date;
    DateFormat dateFormat;
    String date;
    Date currentDate = new Date();
    public CreateTask(JFrame parent,String token) {
        super(parent);

        dp_date.setFormats("dd/MM/yyyy");
        dp_date.setDate(currentDate);

        setTitle("Create Task");
        setContentPane(panel_createtask);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);


        setVisible(true);

        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = dateFormat.format(dp_date.getDate());
                System.out.println(date);

                CreateTaskRequest createTaskRequest = new CreateTaskRequest(tf_taskname.getText(),date,"","","");
                callApiCreateTask(createTaskRequest,token);
            }
        });
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
}
