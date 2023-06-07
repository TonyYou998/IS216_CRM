package org.example.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.io.IOException;
import java.util.Base64;

public class Login extends JDialog {
    private JPanel panel_login;
    private JLabel lb_pass;
    private JButton btn_login;
    private JPanel panel_image;
    private JLabel label_image;
    private JPanel panel_loginInfo;
    private JTextField tf_email;
    private JPasswordField tf_password;
    private String token;


    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(panel_login);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        //label_image.setSize(600,500);
        label_image.setBounds(300,0,450,500);
        // ImageIcon imageIcon = new ImageIcon("src/image/Complete.png");
       ImageIcon imageIcon = new ImageIcon("D:\\courses\\IS216\\crm\\IS216_CRM\\fe\\crm_ui\\src\\image\\Complete.png");
        Image imgScale = imageIcon.getImage().getScaledInstance(label_image.getWidth(),label_image.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaleIcon = new ImageIcon(imgScale);
        label_image.setIcon(scaleIcon);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        if(e.getSource()==btn_login){
                            String username= tf_email.getText();
                            char[] password= tf_password.getPassword();
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

                    String[] chunks = token.split("\\.");
                    Base64.Decoder decoder = Base64.getUrlDecoder();
                    String payload = new String(decoder.decode(chunks[1]));

                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(payload).getAsJsonObject();
                    JsonArray roles = jsonObject.getAsJsonArray("roles");
                    String roleObject = roles.getAsString();
                    System.out.println(roleObject);

                    if (roleObject.equals("ROLE_ADMIN")) {
                        setVisible(false);
                        try {
                            new AdminScreen(null,token);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else  {
                        setVisible(false);
                        try {
//                            new TaskScreen(null,token);
                            new ProjectsScreen(null,token);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<LoginResponse>> call, Throwable throwable) {
                System.out.println("call failure");
            }
        });

    }


}
