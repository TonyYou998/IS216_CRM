package org.example.ui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.dto.GetAllProjectResponse;
import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.GetTaskResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JLabel lb_pjname;
    private JPanel tp_dangXuat;
    private JButton SIGNOUTButton;
    private JLabel lb_1;
    private JLabel lb_2;
    private JLabel lb_3;
    private JLabel lb_4;
    private JLabel lb_5;
    private JLabel label_employees;

    private static List<GetTaskResponse> listAllTask = new ArrayList<>();
    private List<GetTaskResponse> listTaskDone = new ArrayList<>();
    private List<GetTaskResponse> listTaskInPro = new ArrayList<>();
    private List<GetTaskResponse> listTaskBacklog=new ArrayList<>();
    private List<GetTaskResponse> listTaskInProcess=new ArrayList<>();
    private List<GetTaskResponse> listMyTask=new ArrayList<>();
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

    Color color1,color2,color3;

    public TaskScreen(JFrame parent,String token, GetAllProjectResponse projectResponse) throws IOException {
        super(parent);

        encode(token);

        dateFormat =new SimpleDateFormat("dd/MM/yyyy");

        color1 = new Color(227,231,241);
        color2 = new Color(198,203,239);
        color3 = new Color(73,76,162);

        setColorTable(table1);
        setColorTable(table2);
        setColorTable(table3);
        setColorTable(table4);
        setColorTable(table5);
        setColorTable(table6);


        lb_pjname.setText("Project Name: " +projectResponse.getProjectName());
        projectId = Math.toIntExact(projectResponse.getId());

        setTitle("Task Screen");
        setContentPane(panel_taskscreen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        // listAllTask= callApiTask(token,projectId);
        // lstAllEmployee=callApiGetEmployeeInProject(token,projectId);

//        callApiTask(token,projectId);
        callApiGetEmployeeInProject(token,projectId);
//        getTaskBackLog(token,projectId);
//        getTaskInProgress(token,projectId);
//        getMyTask(token, projectId);
//        getTaskDone(token,projectId);

        tp_taskscreen.addTab("Employee",null,tp_employee,null);
        tp_taskscreen.addTab("All tasks",null,tp_alltask,null);
        tp_taskscreen.addTab("My tasks",null,tp_myTasks,null);
        tp_taskscreen.addTab("Backlog",null,tp_backlog,null);
        tp_taskscreen.addTab("In-progess",null,tp_inpro,null);
        tp_taskscreen.addTab("Done",null,tp_done,null);
        tp_taskscreen.addTab("Sign out",null,tp_dangXuat,null);


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

        SIGNOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(null);
            }
        });

        tp_taskscreen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int getSelectedIndex=tp_taskscreen.getSelectedIndex();
                switch (getSelectedIndex){
                    case 1:callApiTask(token,projectId);
                        break;
                    case 2:getMyTask(token,projectId);
                        break;
                    case 3:getTaskBackLog(token,projectId);
                        break;
                    case 4:getTaskInProgress(token,projectId);
                        break;
                    case 5:getTaskDone(token,projectId);
                        break;
                    default:
                        callApiGetEmployeeInProject(token,projectId);
                }
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

    private void setColorTable(JTable table) {
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(color1);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(color2);
        table.getTableHeader().setForeground(color3);
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
                for (int i=0;i<6;i++) {
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
    public List<GetTaskResponse>getMyTask(String token,int projectId){
        Call<MyResponse<List<GetTaskResponse>>> call=ApiClient.callApi().getTaskByUser("Bearer "+ token,projectId);
        try {
            Response<MyResponse<List<GetTaskResponse>>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<List<GetTaskResponse>>lstTask=response.body();
                listMyTask= lstTask.getContent();
                MyTaskTable myTaskTable=new MyTaskTable();
                table6.setModel(myTaskTable);
                for (int i=0;i<6;i++) {
                    table6.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }

//                label_employees.setText(String.valueOf(lstAllEmployee.size())+" Employees");

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void getTaskInProgress(String token,int projectId){

        Call<MyResponse<List<GetTaskResponse>>> call=ApiClient.callApi().getTaskInProgress(token,projectId);
        try {
            Response<MyResponse<List<GetTaskResponse>>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<List<GetTaskResponse>>lstTask=response.body();
                listTaskInProcess= lstTask.getContent();
//                AllBacklogTable allBacklogTable = new AllBacklogTable();
                TaskInProcessTable taskInProcessTable=new TaskInProcessTable();
                table4.setModel(taskInProcessTable);
                for (int i=0;i<6;i++) {
                    table4.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }

//                label_employees.setText(String.valueOf(lstAllEmployee.size())+" Employees");

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void getTaskDone(String token,int projectId){

        Call<MyResponse<List<GetTaskResponse>>> call=ApiClient.callApi().getTaskDone(token,projectId);
        try {
            Response<MyResponse<List<GetTaskResponse>>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<List<GetTaskResponse>>lstTask=response.body();
                listTaskDone= lstTask.getContent();
//                AllBacklogTable allBacklogTable = new AllBacklogTable();
                AllTaskDoneTable allTaskDoneTable=new AllTaskDoneTable();
                table5.setModel(allTaskDoneTable);
                for (int i=0;i<6;i++) {
                    table5.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }

//                label_employees.setText(String.valueOf(lstAllEmployee.size())+" Employees");

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

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
                            table2.setModel(taskInPro);
                        }

//                        } else if(getTaskResponse.getStatus().equals("done")) {
//                            listTaskDone.add(getTaskResponse);
//                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<GetTaskResponse>>> call, Throwable throwable) {
                System.out.println("call failure all task");
            }
        });
    }
    private class MyTaskTable extends AbstractTableModel{
        @Override
        public String getColumnName(int column) {
            return strColTask[column];
        }

        @Override
        public int getRowCount() {
            return listMyTask.size();
        }

        @Override
        public int getColumnCount() {
            return strColTask.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listMyTask.get(rowIndex).getId();
                case 1 -> listMyTask.get(rowIndex).getTaskName();
                case 2 -> {
                    if(listMyTask.get(rowIndex).getAssignEmployeeName()==null)
                        yield "UNASSIGNED";
                    yield listMyTask.get(rowIndex).getAssignEmployeeName();
                }
                case 3 -> changeFormat(listMyTask.get(rowIndex).getStartDate());
                case 4 -> changeFormat(listMyTask.get(rowIndex).getEndDate());
                case 5 -> listMyTask.get(rowIndex).getStatus();
                default -> "-";
            };
        }
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

    private class TaskInProcessTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColTask[column];
        }

        @Override
        public int getRowCount() {
            return listTaskInProcess.size();
        }

        @Override
        public int getColumnCount() {
            return strColTask.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listTaskInProcess.get(rowIndex).getId();
                case 1 -> listTaskInProcess.get(rowIndex).getTaskName();
                case 2 -> {
                    if(listTaskInProcess.get(rowIndex).getAssignEmployeeName()==null)
                        yield "UNASSIGNED";
                    yield listTaskInProcess.get(rowIndex).getAssignEmployeeName();
                }
                case 3 -> changeFormat(listTaskInProcess.get(rowIndex).getStartDate());
                case 4 -> changeFormat(listTaskInProcess.get(rowIndex).getEndDate());
                case 5 -> listTaskInProcess.get(rowIndex).getStatus();
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

    private class AllTaskDoneTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColTask[column];
        }

        @Override
        public int getRowCount() {
            return listTaskDone.size();
        }

        @Override
        public int getColumnCount() {
            return strColTask.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listTaskDone.get(rowIndex).getId();
                case 1 -> listTaskDone.get(rowIndex).getTaskName();
                case 2 -> {
                    if(listTaskDone.get(rowIndex).getAssignEmployeeName()==null)
                        yield "UNASSIGNED";
                    yield listTaskDone.get(rowIndex).getAssignEmployeeName();
                }
                case 3 -> changeFormat(listTaskDone.get(rowIndex).getStartDate());
                case 4 -> changeFormat(listTaskDone.get(rowIndex).getEndDate());
                case 5 -> listTaskDone.get(rowIndex).getStatus();
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
