package org.example.ui;

import org.example.dto.AddEmployeeResponse;
import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.MyResponse;
import org.example.ui.components.ComboBoxItem;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddEmployee extends JDialog {
    private JPanel panel_addemployee;
    private JComboBox cb_username;
    private JButton CANCELButton;
    private JButton SAVEButton;
    private List<GetAllUserAccountResponse> lstEmployee;
    private String userId="-1";

    public AddEmployee(JFrame parent,String token,int pjId) {
        super(parent);

        setTitle("Add Employee");
        setContentPane(panel_addemployee);
        setMinimumSize(new Dimension(500,300));
        setModal(true);
        setLocationRelativeTo(null);

        lstEmployee = AdminScreen.listUser;
        setEmployee(lstEmployee);

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callApi(token,userId,pjId);
            }
        });

        cb_username.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboBoxItem selectedLeader = (ComboBoxItem) cb_username.getSelectedItem();
                userId  = selectedLeader.getId();

                // Use the leaderId and other values as needed
                System.out.println("Selected userID: " + userId);
            }
        });

        setVisible(true);

    }

    private void callApi(String token, String userId,int projectId) {
        Call<MyResponse<AddEmployeeResponse>> getAddEmployee = ApiClient.callApi().getAddEmployee("Bearer "+token,userId,projectId);
        getAddEmployee.enqueue(new Callback<MyResponse<AddEmployeeResponse>>() {
            @Override
            public void onResponse(Call<MyResponse<AddEmployeeResponse>> call, Response<MyResponse<AddEmployeeResponse>> response) {
                if(response.isSuccessful()) {
                    MyResponse<AddEmployeeResponse> myResponse = response.body();
                    if(myResponse.getStatus() == 200) {
                        JOptionPane.showMessageDialog(null, "Successfully added an employee!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<AddEmployeeResponse>> call, Throwable throwable) {

            }
        });
    }
    public void setEmployee(List<GetAllUserAccountResponse> lstEmployee){
        cb_username.addItem(new ComboBoxItem("none","-1"));
        for(GetAllUserAccountResponse item : lstEmployee) {
            if (item.getRoleId().equals("1")) {
                ComboBoxItem comboBoxItem=new ComboBoxItem(item.getUsername(),String.valueOf(item.getId()));
                cb_username.addItem(comboBoxItem);
            }
        }
        cb_username.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                // Display only the username in the JComboBox
                return super.getListCellRendererComponent(list, ((ComboBoxItem) value).getDisplayText(), index,
                        isSelected, cellHasFocus);
            }
        });
    }
}
