package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Login extends JDialog {
    private JPanel panel_login;
    private JTextField tf_username;
    private JPasswordField tf_password;
    private JButton btn_login;
    private JPanel panel_image;
    private JLabel label_image;
    private JPanel panel_loginInfo;

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

        setVisible(true);
    }

}
