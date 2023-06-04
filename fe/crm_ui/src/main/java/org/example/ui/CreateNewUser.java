package org.example.ui;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.dto.MyResponse;
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
    private JFrame lastScreen;

    public CreateNewUser(JFrame parent, String token) {
        super(parent);
        this.lastScreen=parent;

        cb_role.addItem("Employee");
        cb_role.addItem("Admin");
        cb_role.addItem("Leader");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dp_date.setFormats(dateFormat);
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                date = simpleDateFormat.format(dp_date.getDate());

                CreateUserRequest createUserRequest = new CreateUserRequest(tf_username.getText(),visiblePassword,tf_phone.getText(),tf_fullname.getText(),tf_address.getText(),tf_email.getText(),date,roleId);
                CreateUserResponse newUser= callApiCreateUser(createUserRequest,token);
                if(newUser !=null)
                   dispose();
            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }


    private CreateUserResponse callApiCreateUser(CreateUserRequest createUserRequest,String token) {

        Call<MyResponse<CreateUserResponse>> call = ApiClient.callApi().postCreateUser(createUserRequest,"Bearer "+token);

        try{
            Response<MyResponse<CreateUserResponse>> response=call.execute();
            if(response.isSuccessful()){
                MyResponse<CreateUserResponse> responseBody=response.body();
                return responseBody.getContent();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
