package org.example.ui;

import org.example.dto.GetAllProjectResponse;
import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.GetTaskResponse;
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

    String[] strColPj = {"Id","Name","Start date", "End date","Leader"};
    String[] strColUser = {"Id","Username","Role","Phone", "Fullname","Address","Email"};

    List<GetAllProjectResponse> listPj = new ArrayList<>();

    static List<GetAllUserAccountResponse> listUser = new ArrayList<>();

    List<String> listRole = new ArrayList<>();
    DateFormat dateFormat;
    DefaultTableCellRenderer cellRenderer;
    String token;

    public AdminScreen(JFrame parent,String token) throws IOException {
        super(parent);
        this.token=token;
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

//        BufferedImage buttonIcon = ImageIO.read(new File("src/image/add.png"));
        BufferedImage buttonIcon = ImageIO.read(new File("D:\\courses\\IS216\\crm\\IS216_CRM\\fe\\crm_ui\\src\\image\\add.png"));
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

        btn_user_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(tf_user_search.getText());

            }
        });
        tableUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle right-click event
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = tableUser.rowAtPoint(e.getPoint());
                    int col = tableUser.columnAtPoint(e.getPoint());
                    // Show the popup at the selected location

                    int pjId = Integer.parseInt(tablePj.getValueAt(tablePj.rowAtPoint(e.getPoint()), 0).toString());

                    showPopup(e.getComponent(), e.getX(), e.getY(), row, col,pjId);
                }
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
                System.out.println("Popup Item Clicked at row " + row + ", column " + col);
            }
        });
        popup.add(item1);
        popup.add(item2);
        popup.show(component, x, y);
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
                case 2 -> changeFormat(listPj.get(rowIndex).getStartDate());
                case 3 -> changeFormat(listPj.get(rowIndex).getEndDate());
                case 4 -> listPj.get(rowIndex).getLeaderName();
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


