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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private JButton refreshButton;
    private JButton refreshButton1;
    private JScrollPane refreshBtn;
    private JPanel tp_dangxuat;
    private JButton SIGNOUTButton;

    String[] strColPj = {"Id","Name","Start date", "End date","Leader"};
    String[] strColUser = {"Id","Username","Role","Phone", "Fullname","Address","Email"};

    List<GetAllProjectResponse> listPj = new ArrayList<>();

    List<GetAllProjectResponse> listTamPj = new ArrayList<>();

    static List<GetAllUserAccountResponse> listUser = new ArrayList<>();

    List<GetAllUserAccountResponse> listTamUser = new ArrayList<>();


    List<String> listRole = new ArrayList<>();
    DateFormat dateFormat;
    DefaultTableCellRenderer cellRenderer;
    String token;

    TableRowSorter sorter;

    public AdminScreen(JFrame parent,String token) throws IOException {
        super(parent);
        this.token=token;

        Color color1 = new Color(227,231,241);
        Color color2 = new Color(198,203,239);
        Color color3 = new Color(73,76,162);

        tablePj.setOpaque(true);
        tablePj.setFillsViewportHeight(true);
        tablePj.setBackground(color1);
        tablePj.getTableHeader().setOpaque(false);
        tablePj.getTableHeader().setBackground(color2);
        tablePj.getTableHeader().setForeground(color3);

        tableUser.setOpaque(true);
        tableUser.setFillsViewportHeight(true);
        tableUser.setBackground(color1);
        tableUser.getTableHeader().setOpaque(false);
        tableUser.getTableHeader().setBackground(color2);
        tableUser.getTableHeader().setForeground(color3);

        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        callApiAllPj(token);
        callApiAllUser(token);
        dateFormat =new SimpleDateFormat("dd/MM/yyyy");

        setTitle("AdminScreen");
        setContentPane(panel_admin_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        tp_adminscreen.addTab("Projects",null,tp_pj,null);
        tp_adminscreen.addTab("Users",null,tp_user,null);
        tp_adminscreen.addTab("Sign out",null,tp_dangxuat,null);

        BufferedImage buttonIcon = ImageIO.read(new File("src/image/add.png"));
//        BufferedImage buttonIcon = ImageIO.read(new File("D:\\courses\\IS216\\crm\\IS216_CRM\\fe\\crm_ui\\src\\image\\add.png"));
        btn_pj_add.setIcon(new ImageIcon(buttonIcon));
        btn_pj_add.setBorder(BorderFactory.createEmptyBorder());
        btn_pj_add.setContentAreaFilled(false);
        btn_user_add.setIcon(new ImageIcon(buttonIcon));
        btn_user_add.setBorder(BorderFactory.createEmptyBorder());
        btn_user_add.setContentAreaFilled(false);


        btn_pj_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateNewProject(null,token);
                callApiAllPj(token);
            }
        });

        btn_user_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateNewUser(null,token);
                callApiAllUser(token);
            }
        });

        tablePj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle right-click event
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = tablePj.rowAtPoint(e.getPoint());
                    int col = tablePj.columnAtPoint(e.getPoint());
                    // Show the popup at the selected location
                    int pjId = Integer.parseInt(tablePj.getValueAt(tablePj.rowAtPoint(e.getPoint()), 0).toString());
                    System.out.println(pjId);
                    showPopup(e.getComponent(), e.getX(), e.getY(), row, col,pjId);
                }
            }
        }
        );

        btn_pj_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tf_pj_search.getText().toLowerCase();

                listTamPj.clear();

                listPj.size();

                for(GetAllProjectResponse projectResponse : listPj) {
                    if(projectResponse.getProjectName().toLowerCase().contains(text)) {
                        listTamPj.add(projectResponse);
                    }
                }

                ProjectTable projectTable = new ProjectTable();
                tablePj.setModel(projectTable);
                for (int i=0;i<5;i++) {
                    tablePj.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }
            }
        });

        btn_user_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tf_user_search.getText().toLowerCase();

                listTamUser.clear();
                listUser.size();

                for(GetAllUserAccountResponse userAccountResponse : listUser) {
                    if(userAccountResponse.getUsername().toLowerCase().contains(text)) {
                        listTamUser.add(userAccountResponse);
                    }
                }

                UserTable userTable = new UserTable();
                tableUser.setModel(userTable);
                for (int i=0;i<5;i++) {
                    tableUser.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                }
            }
        });

        SIGNOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(null);
            }
        });
        setVisible(true);
    }

    public void showPopup(Component component, int x, int y, int row, int col,int pjId) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("Add employee");
        JMenuItem item2 = new JMenuItem("Delete");

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action when the popup item is clicked
//                System.out.println("Popup Item Clicked at row " + row + ", column " + col);
                new AddEmployee(null,token,pjId);
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action when the popup item is clicked
//                System.out.println("Popup Item Clicked at row " + row + ", column " + col);
                String deleteStatus=delete(token,pjId);
                System.out.println(deleteStatus);
                callApiAllPj(token);
            }
        });
        popup.add(item1);
        popup.add(item2);
        popup.show(component, x, y);
    }

    private String delete(String token, int pjId) {
        Call<String> call=ApiClient.callApi().deleteProject("Bearer "+token,String.valueOf(pjId));
        try{
            Response<String> response= call.execute();
            if(response.isSuccessful()){

                String result = "success";
                return  result;
            }
        }
        catch (Exception ex){
            return null;
        }
        return null;
    }

    public void callApiAllPj(String token) {
        Call<java.util.List<GetAllProjectResponse>> responese = ApiClient.callApi().getAllProjectForAdmin("Bearer "+token);
        responese.enqueue(new Callback<java.util.List<GetAllProjectResponse>>() {
            @Override
            public void onResponse(Call<java.util.List<GetAllProjectResponse>> call, Response<java.util.List<GetAllProjectResponse>> response) {
                if(response.isSuccessful()){
                    listPj = response.body();
                    listTamPj = listPj;
                    ProjectTable projectTable = new ProjectTable();
                    tablePj.setModel(projectTable);
                    for (int i=0;i<5;i++) {
                        tablePj.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                    }
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
        if(!listRole.isEmpty())
            listRole.clear();
        reponse.enqueue(new Callback<List<GetAllUserAccountResponse>>() {
            @Override
            public void onResponse(Call<List<GetAllUserAccountResponse>> call, Response<List<GetAllUserAccountResponse>> response) {
                if(response.isSuccessful()){
                    listUser = response.body();
                    listTamUser = listUser;
                    for(int i=0;i<listTamUser.size();i++) {
                        if(listTamUser.get(i).getRoleId().equals("1")) {listRole.add("Employee");}
                        else if(listTamUser.get(i).getRoleId().equals("2")) {listRole.add("Admin");}
                        else if(listTamUser.get(i).getRoleId().equals("3")) {listRole.add("Leader");}
                    }
                    UserTable userTable = new UserTable();
                    tableUser.setModel(userTable);
                    for (int i=0;i<7;i++) {
                        tableUser.getColumnModel().getColumn(i).setCellRenderer( cellRenderer );
                    }
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
            return listTamPj.size();
        }

        @Override
        public int getColumnCount() {
            return strColPj.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listTamPj.get(rowIndex).getId();
                case 1 -> listTamPj.get(rowIndex).getProjectName();
                case 2 -> changeFormat(listTamPj.get(rowIndex).getStartDate());
                case 3 -> changeFormat(listTamPj.get(rowIndex).getEndDate());
                case 4 -> listTamPj.get(rowIndex).getLeaderName();
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
            return listTamUser.size();
        }

        @Override
        public int getColumnCount() {
            return strColUser.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> listTamUser.get(rowIndex).getId();
                case 1 -> listTamUser.get(rowIndex).getUsername();
                case 2 -> listRole.get(rowIndex);
                case 3 -> listTamUser.get(rowIndex).getPhone();
                case 4 -> listTamUser.get(rowIndex).getFullName();
                case 5 -> listTamUser.get(rowIndex).getAddress();
                case 6 -> listTamUser.get(rowIndex).getEmail();
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


