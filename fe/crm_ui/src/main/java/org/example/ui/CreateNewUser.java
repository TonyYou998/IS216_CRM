package org.example.ui;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.utils.ApiClient;
import org.jdesktop.swingx.JXDatePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewUser extends JDialog{
    private JTextField tf_username;
    private JTextField tf_fullname;
    private JPasswordField tf_password;
    private JTextField tf_email;
    private JTextField tf_phone;
    private JComboBox cb_role;
    private JPanel panel_createnewuser;
    private JTextField tf_address;
    private JButton CREATEButton;
    private JButton CANCELButton;
    private JXDatePicker dp_date;
    private JButton btn_create;

    DateFormat dateFormat;
    String date;
    Date currentDate = new Date();

    public CreateNewUser(JFrame parent, String token) {
        super(parent);

        cb_role.addItem("Employee");
        cb_role.addItem("Admin");
        cb_role.addItem("Leader");

        dp_date.setFormats("dd/MM/yyyy");
        dp_date.setDate(currentDate);

        setTitle("Create new user");
        setContentPane(panel_createnewuser);
        setMinimumSize(new Dimension(550,350));
        setModal(true);
        setLocationRelativeTo(null);

        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valueComboBox = cb_role.getItemAt(cb_role.getSelectedIndex()).toString();
                String roleId = "";
                if(valueComboBox.equals("Employee")) {roleId = "1";}
                else if(valueComboBox.equals("Admin")) {roleId = "2";}
                else if(valueComboBox.equals("Leader")) {roleId = "3";}

                String visiblePassword=new String(tf_password.getPassword());

                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = dateFormat.format(dp_date.getDate());

                CreateUserRequest createUserRequest = new CreateUserRequest(tf_username.getText(),visiblePassword,tf_phone.getText(),tf_fullname.getText(),tf_address.getText(),tf_email.getText(),date,roleId);
                callApiCreateUser(createUserRequest,token);
            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setVisible(true);

    }

    private void callApiCreateUser(CreateUserRequest createUserRequest,String token) {
        Call<CreateUserResponse> call = ApiClient.callApi().postCreateUser(createUserRequest,"Bearer "+token);
        call.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
//                System.out.println("call ok");
                if (response.isSuccessful()) {
                    CreateUserResponse createUserResponse = response.body();
                    try {
                        new AdminScreen(null,token);
                        setVisible(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateUserResponse> call, Throwable throwable) {
                System.out.println("call failure");
            }
        });

    }
}
