package org.example.ui;

import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Login extends JDialog {
    private JPanel panel_login;
    private JTextField tf_username;
    private JPasswordField tf_password;
    private JButton btn_login;
    private JPanel panel_image;
    private JLabel label_image;
    private JPanel panel_loginInfo;
    private String token;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(panel_login);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        //label_image.setSize(600,500);
        label_image.setBounds(200,0,500,500);
        ImageIcon imageIcon = new ImageIcon("G:\\Oanhhh\\java\\IS216_CRM\\fe\\crm_ui\\src\\image\\Complete.png");
        Image imgScale = imageIcon.getImage().getScaledInstance(label_image.getWidth(),label_image.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaleIcon = new ImageIcon(imgScale);
        label_image.setIcon(scaleIcon);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        if(e.getSource()==btn_login){
                            String username= tf_username.getText();
                            char[] password=tf_password.getPassword();
                            String visiblePassword=new String(password);
                           if(!username.isEmpty()&&!visiblePassword.isEmpty()){
                               LoginRequest request=new LoginRequest(username,visiblePassword);
                                callLoginApi(request);
                           }
                        }
            }
        });

        setVisible(true);

    }



    public void callLoginApi(LoginRequest request){

        Call<MyResponse<LoginResponse>> loginResponseCall= ApiClient.callApi().loginResponseCall(request);
         loginResponseCall.enqueue(new Callback<MyResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<MyResponse<LoginResponse>> call, Response<MyResponse<LoginResponse>> response) {
                if(response.isSuccessful()){
                    MyResponse<LoginResponse> loginResponse=response.body();
                    LoginResponse content=loginResponse.getContent();
                    token=content.getToken();
                    System.out.println(token);
                }

            }

            @Override
            public void onFailure(Call<MyResponse<LoginResponse>> call, Throwable throwable) {
                System.out.println("call failure");
            }
        });

    }

}
