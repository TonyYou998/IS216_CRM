package org.example.ui;

import org.example.dto.GetAllProjectResponse;
import org.example.dto.GetAllUserAccountResponse;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminScreen extends JDialog {

    private JPanel panel_admin_screen;
    private JTabbedPane tp_adminscreen;
    private JPanel tp_pj;
    private JPanel tp_user;
    private JTextField tf_pj_search;
    private JButton btn_pj_search;
    private JLabel lb_pj_search;
    private JButton btn_user_search;
    private JTextField tf_user_search;
    private JLabel lb_user_search;
    private JTable tablePj;
    private JTable tableUser;
    private JButton btn_pj_add;
    private JButton btn_user_add;

    String[] strColPj = {"Id","Name","Start date", "End date","Leader"};
    String[] strColUser = {"Id","Username","RoleId","Phone", "Fullname","Address","Email"};

    List<GetAllProjectResponse> listPj;

    List<GetAllUserAccountResponse> listUser;

    List<String> listRole = new ArrayList<>();


    public AdminScreen(JFrame parent,String token) throws IOException {
        super(parent);

        callApiAllPj(token);
        callApiAllUser(token);

        setTitle("AdminScreen");
        setContentPane(panel_admin_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);


        tp_adminscreen.addTab("Projects",null,tp_pj,null);
        tp_adminscreen.addTab("Users",null,tp_user,null);

        BufferedImage buttonIcon = ImageIO.read(new File("src/image/add.png"));
        btn_pj_add.setIcon(new ImageIcon(buttonIcon));
        btn_pj_add.setBorder(BorderFactory.createEmptyBorder());
        btn_pj_add.setContentAreaFilled(false);

        btn_user_add.setIcon(new ImageIcon(buttonIcon));
        btn_user_add.setBorder(BorderFactory.createEmptyBorder());
        btn_user_add.setContentAreaFilled(false);

        btn_pj_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateNewProject(null);

            }
        });

        btn_user_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateNewUser(null,token);
            }
        });


        setVisible(true);


    }

    public void callApiAllPj(String token) {
        Call<java.util.List<GetAllProjectResponse>> responese = ApiClient.callApi().getAllProjectForAdmin("Bearer "+token);
        responese.enqueue(new Callback<java.util.List<GetAllProjectResponse>>() {
            @Override
            public void onResponse(Call<java.util.List<GetAllProjectResponse>> call, Response<java.util.List<GetAllProjectResponse>> response) {
                if(response.isSuccessful()){
                    listPj = response.body();
                    ProjectTable projectTable = new ProjectTable();
                    tablePj.setModel(projectTable);

                }

            }

            @Override
            public void onFailure(Call<List<GetAllProjectResponse>> call, Throwable throwable) {
                System.out.print("call failure Pj");
            }
        });
    }

    public void callApiAllUser(String token) {
        Call<List<GetAllUserAccountResponse>> reponse = ApiClient.callApi().getAllUser("Bearer "+token);
        reponse.enqueue(new Callback<List<GetAllUserAccountResponse>>() {
            @Override
            public void onResponse(Call<List<GetAllUserAccountResponse>> call, Response<List<GetAllUserAccountResponse>> response) {
                if(response.isSuccessful()){
                    listUser = response.body();
                    for(int i=0;i<listUser.size();i++) {
                        if(listUser.get(i).getRoleId().equals("1")) {listRole.add("Employee");}
                        else if(listUser.get(i).getRoleId().equals("2")) {listRole.add("Admin");}
                        else if(listUser.get(i).getRoleId().equals("3")) {listRole.add("Leader");}
                    }

                    UserTable userTable = new UserTable();
                    tableUser.setModel(userTable);
                }
            }

            @Override
            public void onFailure(Call<List<GetAllUserAccountResponse>> call, Throwable throwable) {
                System.out.print("call failure user");

            }
        });
    }

    private class ProjectTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColPj[column];
        }

        @Override
        public int getRowCount() {
            return listPj.size();
        }

        @Override
        public int getColumnCount() {
            return strColPj.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listPj.get(rowIndex).getId();
                case 1 -> listPj.get(rowIndex).getProjectName();
                case 2 -> listPj.get(rowIndex).getStartDate();
                case 3 -> listPj.get(rowIndex).getEndDate();
                case 4 -> listPj.get(rowIndex).getLeaderId();
                default -> "-";
            };
        }
    }

    private class UserTable extends AbstractTableModel {

        @Override
        public String getColumnName(int column) {
            return strColUser[column];
        }

        @Override
        public int getRowCount() {
            return listUser.size();
        }

        @Override
        public int getColumnCount() {
            return strColUser.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listUser.get(rowIndex).getId();
                case 1 -> listUser.get(rowIndex).getUsername();
                case 2 -> listRole.get(rowIndex);
                case 3 -> listUser.get(rowIndex).getPhone();
                case 4 -> listUser.get(rowIndex).getFullName();
                case 5 -> listUser.get(rowIndex).getAddress();
                case 6 -> listUser.get(rowIndex).getEmail();
                default -> "-";
            };
        }
    }

}
