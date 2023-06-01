package org.example.ui;

import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.GetTaskResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JScrollPane table_user;
    private JScrollPane table_task;
    private JPanel tp_myTasks;
    private JTable table6;
    private JButton refreshButton;

    private  List<GetTaskResponse> listAllTask;
    private List<GetAllUserAccountResponse> lstAllEmployee;

    String[] strColTask = {"TaskID","Task Name","Assignee","Start date", "End date","Status"};
    String[] strColUser = {"Id","Username","Role","Phone", "Fullname","Address","Email"};

    public TaskScreen(JFrame parent,String token) throws IOException {
        super(parent);

        setTitle("Task Screen");
        setContentPane(panel_taskscreen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        callApiTask(token,1);
        lstAllEmployee=callApiGetEmployeeInProject(token,1);


        tp_taskscreen.addTab("Employee",null,tp_employee,null);
        tp_taskscreen.addTab("All tasks",null,tp_alltask,null);
        tp_taskscreen.addTab("My tasks",null,tp_myTasks,null);
        tp_taskscreen.addTab("Backlog",null,tp_backlog,null);
        tp_taskscreen.addTab("In-progess",null,tp_inpro,null);
        tp_taskscreen.addTab("Done",null,tp_done,null);

        BufferedImage buttonIcon = ImageIO.read(new File("D:\\courses\\IS216\\crm\\IS216_CRM\\fe\\crm_ui\\src\\image\\add.png"));
        btn_employee_create.setIcon(new ImageIcon(buttonIcon));
        btn_employee_create.setBorder(BorderFactory.createEmptyBorder());
        btn_employee_create.setContentAreaFilled(false);

        btn_alltask_create.setIcon(new ImageIcon(buttonIcon));
        btn_alltask_create.setBorder(BorderFactory.createEmptyBorder());
        btn_alltask_create.setContentAreaFilled(false);
        AllEmployeeTable allEmployeeTable=new AllEmployeeTable();
        table1.setModel(allEmployeeTable);
        btn_alltask_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateTask(null,token);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callApiTask(token,1);
            }
        });
        btn_employee_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployee(null,token);
            }
        });

        setVisible(true);


    }

    public static List<GetAllUserAccountResponse> callApiGetEmployeeInProject(String token, int projectId) {
        Call<MyResponse<List<GetAllUserAccountResponse>>> call=ApiClient.callApi().getAllEmployeeInProject("Bearer "+token,projectId);
        try {
            Response<MyResponse<List<GetAllUserAccountResponse>>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<List<GetAllUserAccountResponse>>lstEmployee=response.body();
                return lstEmployee.getContent();
            }
        }
        catch (IOException e) {

            throw new RuntimeException(e);
        }
return null;
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
                    AllTaskTable allTaskTable = new AllTaskTable();
                    table2.setModel(allTaskTable);
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<GetTaskResponse>>> call, Throwable throwable) {
                System.out.print("call failure all task");

            }
        });
    }



    private class AllTaskTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColTask[column];
        }

        @Override
        public int getRowCount() {
            return listAllTask.size();
        }

        @Override
        public int getColumnCount() {
            return strColTask.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listAllTask.get(rowIndex).getId();
                case 1 -> listAllTask.get(rowIndex).getTaskName();
                case 2 -> {
                    if(listAllTask.get(rowIndex).getAssignEmployeeName()==null)
                        yield "UNASSIGNED";
                    yield listAllTask.get(rowIndex).getAssignEmployeeName();
                }
                case 3 -> listAllTask.get(rowIndex).getStartDate();
                case 4 -> listAllTask.get(rowIndex).getEndDate();
                case 5 -> listAllTask.get(rowIndex).getStatus();
                default -> "-";
            };
        }
    }
    private class AllEmployeeTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColUser[column];
        }

        @Override
        public int getRowCount() {
            return lstAllEmployee.size();
        }

        @Override
        public int getColumnCount() {
            return strColUser.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> lstAllEmployee.get(rowIndex).getId();
                case 1 -> lstAllEmployee.get(rowIndex).getUsername();
                case 2 -> lstAllEmployee.get(rowIndex).getRoleName();
                case 3 -> lstAllEmployee.get(rowIndex).getPhone();
                case 4 -> lstAllEmployee.get(rowIndex).getFullName();
                case 5 -> lstAllEmployee.get(rowIndex).getAddress();
                case 6->lstAllEmployee.get(rowIndex).getEmail();
                default -> "-";
            };
        }
    }
}
