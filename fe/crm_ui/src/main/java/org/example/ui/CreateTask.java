package org.example.ui;

import org.example.dto.CreateTaskRequest;
import org.example.dto.CreateTaskResponse;
import org.example.dto.CreateUserRequest;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JTextField tf_duedate;
    private JComboBox cb_employee;
    private JButton btn_save;
    private JButton btn_cancel;
    private JPanel panel_createtask;

    public CreateTask(JFrame parent,String token) {
        super(parent);
        setTitle("Create Task");
        setContentPane(panel_createtask);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateTaskRequest createTaskRequest = new CreateTaskRequest(tf_taskname.getText(),"","","","");
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
