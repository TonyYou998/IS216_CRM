package org.example.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.GetTaskResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
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
    private JLabel label_employees;

    private static List<GetTaskResponse> listAllTask = new ArrayList<>();
    private List<GetTaskResponse> listTaskDone = new ArrayList<>();
    private List<GetTaskResponse> listTaskInPro = new ArrayList<>();
    private List<GetTaskResponse> listTaskBacklog=new ArrayList<>();
    public static List<GetAllUserAccountResponse> lstAllEmployee = new ArrayList<>();
    DateFormat dateFormat;
    GetTaskResponse getTaskResponseSelection;
    private JButton refreshButton;
    private int projectId;
//    private  List<GetTaskResponse> listAllTask;
    // private List<GetAllUserAccountResponse> lstAllEmployee;

    String[] strColTask = {"TaskID","Task Name","Assignee","Start date", "End date","Status"};
    String[] strColUser = {"Id","Username","Role","Phone", "Fullname","Address","Email"};
    String userName;
    DefaultTableCellRenderer cellRenderer;

    public TaskScreen(JFrame parent,String token, int projectId) throws IOException {
        super(parent);

        encode(token);

        dateFormat =new SimpleDateFormat("dd/MM/yyyy");

        setTitle("Task Screen");
        setContentPane(panel_taskscreen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        // listAllTask= callApiTask(token,projectId);
        // lstAllEmployee=callApiGetEmployeeInProject(token,projectId);

        callApiTask(token,projectId);
        callApiGetEmployeeInProject(token,projectId);
        getTaskBackLog(token,projectId);

        tp_taskscreen.addTab("Employee",null,tp_employee,null);
        tp_taskscreen.addTab("All tasks",null,tp_alltask,null);
        tp_taskscreen.addTab("My tasks",null,tp_myTasks,null);
        tp_taskscreen.addTab("Backlog",null,tp_backlog,null);
        tp_taskscreen.addTab("In-progess",null,tp_inpro,null);
        tp_taskscreen.addTab("Done",null,tp_done,null);

//        BufferedImage buttonIcon = ImageIO.read(new File("src/image/add.png"));
        BufferedImage buttonIcon = ImageIO.read(new File("D:\\courses\\IS216\\crm\\IS216_CRM\\fe\\crm_ui\\src\\image\\add.png"));
//        btn_employee_create.setIcon(new ImageIcon(buttonIcon));
//        btn_employee_create.setBorder(BorderFactory.createEmptyBorder());
//        btn_employee_create.setContentAreaFilled(false);

        btn_alltask_create.setIcon(new ImageIcon(buttonIcon));
        btn_alltask_create.setBorder(BorderFactory.createEmptyBorder());
        btn_alltask_create.setContentAreaFilled(false);

        btn_alltask_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateTask(null,token,projectId);
                callApiTask(token,projectId);
            }
        });

        table2.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
//                System.out.println(table2.getValueAt(table2.getSelectedRow(), 0).toString());
                String valueSelected = table2.getValueAt(table2.getSelectedRow(), 0).toString();
                for (GetTaskResponse getTaskResponse : listAllTask) {
                    if(valueSelected.equals(String.valueOf(getTaskResponse.getId()))) {
                        getTaskResponseSelection = getTaskResponse;
                    }
                }
                new EditTask(null,getTaskResponseSelection,token);
                callApiTask(token,projectId);
            }
        });


        setVisible(true);
    }

    private void encode(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(payload).getAsJsonObject();
        userName = String.valueOf(jsonObject.get("sub"));
        System.out.println(userName);

    }
    private void callApiGetEmployeeInProject(String token, int projectId) {
        Call<MyResponse<List<GetAllUserAccountResponse>>> call=ApiClient.callApi().getAllEmployeeInProject("Bearer "+token,projectId);
        try {
            Response<MyResponse<List<GetAllUserAccountResponse>>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<List<GetAllUserAccountResponse>>lstEmployee=response.body();
                lstAllEmployee = lstEmployee.getContent();
                AllEmployeeTable allUserTable = new AllEmployeeTable();
                table1.setModel(allUserTable);
                for (int i=0;i<7;i++) {
                    table1.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }

                label_employees.setText(String.valueOf(lstAllEmployee.size())+" Employees");

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<GetTaskResponse>getTaskBackLog(String token,int projectId){
        Call<MyResponse<List<GetTaskResponse>>> call=ApiClient.callApi().getAllTaskInBackLog(token,projectId);
        try {
            Response<MyResponse<List<GetTaskResponse>>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<List<GetTaskResponse>>lstTask=response.body();
                listTaskBacklog= lstTask.getContent();
                AllBacklogTable allBacklogTable = new AllBacklogTable();
                table3.setModel(allBacklogTable);
                for (int i=0;i<7;i++) {
                    table1.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }

//                label_employees.setText(String.valueOf(lstAllEmployee.size())+" Employees");

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
                if(response.isSuccessful()){
                    MyResponse<List<GetTaskResponse>> listMyResponse = response.body();
                    listAllTask = listMyResponse.getContent();
                    AllTaskTable allTaskTable = new AllTaskTable();
                    table2.setModel(allTaskTable);
                    for (int i=0;i<6;i++) {
                        table2.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                    }

                    for(GetTaskResponse getTaskResponse : listAllTask) {
                        if(getTaskResponse.getStatus().equals("in-progress")) {
                            listTaskInPro.add(getTaskResponse);
                            TaskInProTable taskInPro = new TaskInProTable();
                            table4.setModel(taskInPro);

                        } else if(getTaskResponse.getStatus().equals("done")) {
                            listTaskDone.add(getTaskResponse);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<GetTaskResponse>>> call, Throwable throwable) {
                System.out.println("call failure all task");
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
                case 3 -> changeFormat(listAllTask.get(rowIndex).getStartDate());
                case 4 -> changeFormat(listAllTask.get(rowIndex).getEndDate());
                case 5 -> listAllTask.get(rowIndex).getStatus();
                default -> "-";
            };
        }
    }
    private class AllBacklogTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColTask[column];
        }

        @Override
        public int getRowCount() {
            return listTaskBacklog.size();
        }

        @Override
        public int getColumnCount() {
            return strColTask.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listTaskBacklog.get(rowIndex).getId();
                case 1 -> listTaskBacklog.get(rowIndex).getTaskName();
                case 2 -> {
                    if(listTaskBacklog.get(rowIndex).getAssignEmployeeName()==null)
                        yield "UNASSIGNED";
                    yield listTaskBacklog.get(rowIndex).getAssignEmployeeName();
                }
                case 3 -> changeFormat(listTaskBacklog.get(rowIndex).getStartDate());
                case 4 -> changeFormat(listTaskBacklog.get(rowIndex).getEndDate());
                case 5 -> listTaskBacklog.get(rowIndex).getStatus();
                default -> "-";
            };
        }
    }
    private class TaskInProTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColTask[column];
        }

        @Override
        public int getRowCount() {
            return listTaskInPro.size();
        }

        @Override
        public int getColumnCount() {
            return strColTask.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listTaskInPro.get(rowIndex).getId();
                case 1 -> listTaskInPro.get(rowIndex).getTaskName();
                case 2 -> {
                    if(listTaskInPro.get(rowIndex).getAssignEmployeeName()==null)
                        yield "UNASSIGNED";
                    yield listTaskInPro.get(rowIndex).getAssignEmployeeName();
                }
                case 3 -> changeFormat(listTaskInPro.get(rowIndex).getStartDate());
                case 4 -> changeFormat(listTaskInPro.get(rowIndex).getEndDate());
                case 5 -> listTaskInPro.get(rowIndex).getStatus();
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

    private String changeFormat(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(date);
            String strDate = dateFormat.format(parsedDate);
            return strDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
