package org.example.ui;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.dto.LoginRequest;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateNewUser extends JDialog{
    private JTextField tf_username;
    private JTextField tf_fullname;
    private JPasswordField tf_password;
    private JTextField tf_email;
    private JTextField tf_dateofbirth;
    private JTextField tf_phone;
    private JComboBox cb_role;
    private JPanel panel_createnewuser;
    private JTextField tf_address;
    private JButton btn_create;
    private JButton btn_cancel;

    public CreateNewUser(JFrame parent, String token) {
        super(parent);

        cb_role.addItem("Employee");
        cb_role.addItem("Admin");
        cb_role.addItem("Leader");


        setTitle("Create new user");
        setContentPane(panel_createnewuser);
        setMinimumSize(new Dimension(550,350));
        setModal(true);
        setLocationRelativeTo(null);

        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valueComboBox = cb_role.getItemAt(cb_role.getSelectedIndex()).toString();
                String roleId = "";
                if(valueComboBox.equals("Employee")) {roleId = "1";}
                else if(valueComboBox.equals("Admin")) {roleId = "2";}
                else if(valueComboBox.equals("Leader")) {roleId = "3";}
                System.out.println(roleId);

                String visiblePassword=new String(tf_password.getPassword());

                CreateUserRequest createUserRequest = new CreateUserRequest(tf_username.getText(),visiblePassword,tf_phone.getText(),tf_fullname.getText(),tf_address.getText(),tf_email.getText(),"",roleId);
                callApiCreateUser(createUserRequest,token);
            }
        });
        setVisible(true);

    }

    private void callApiCreateUser(CreateUserRequest createUserRequest,String token) {
        Call<CreateUserResponse> call = ApiClient.callApi().postCreateUser(createUserRequest,"Bearer "+token);
        call.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                System.out.println("call ok");
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
