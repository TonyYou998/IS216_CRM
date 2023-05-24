package org.example.ui;

import org.example.dto.GetAllProjectResponse;
import org.example.dto.GetTaskResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TaskScreen extends JDialog {
    private JPanel panel_taskscreen;
    private JTabbedPane tp_taskscreen;
    private JTable table1;
    private JButton btn_employee_create;
    private JButton btn_alltask_create;
    private JTable table2;
    private JPanel tp_employee;
    private JPanel tp_alltask;
    private JTable table3;
    private JTable table4;
    private JTable table5;
    private JPanel tp_backlog;
    private JPanel tp_inpro;
    private JPanel tp_done;

    private  List<GetTaskResponse> listAllTask;

    public TaskScreen(JFrame parent,String token) throws IOException {
        super(parent);
        setTitle("Task Screen");
        setContentPane(panel_taskscreen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        callApiTask(token,1);

        tp_taskscreen.addTab("Employee",null,tp_employee,null);
        tp_taskscreen.addTab("All tasks",null,tp_alltask,null);
        tp_taskscreen.addTab("Backlog",null,tp_backlog,null);
        tp_taskscreen.addTab("In-progess",null,tp_inpro,null);
        tp_taskscreen.addTab("Done",null,tp_done,null);

        BufferedImage buttonIcon = ImageIO.read(new File("src/image/add.png"));
        btn_employee_create.setIcon(new ImageIcon(buttonIcon));
        btn_employee_create.setBorder(BorderFactory.createEmptyBorder());
        btn_employee_create.setContentAreaFilled(false);

        btn_alltask_create.setIcon(new ImageIcon(buttonIcon));
        btn_alltask_create.setBorder(BorderFactory.createEmptyBorder());
        btn_alltask_create.setContentAreaFilled(false);


        setVisible(true);

    }

    private void callApiTask(String token, int id) {
        Call<MyResponse<List<GetTaskResponse>>> myResponseCall = ApiClient.callApi().getTaskByProjectId("Bearer "+token,id);
        myResponseCall.enqueue(new Callback<MyResponse<List<GetTaskResponse>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<GetTaskResponse>>> call, Response<MyResponse<List<GetTaskResponse>>> response) {
                System.out.print("ok");
                MyResponse<List<GetTaskResponse>> listMyResponse = response.body();
                listAllTask = listMyResponse.getContent();
                if (listAllTask == null) {
                    System.out.print("null");
                } else {
                    System.out.print(listAllTask.size());
                }

            }

            @Override
            public void onFailure(Call<MyResponse<List<GetTaskResponse>>> call, Throwable throwable) {
                System.out.print("call failure all task");

            }
        });
    }
}
