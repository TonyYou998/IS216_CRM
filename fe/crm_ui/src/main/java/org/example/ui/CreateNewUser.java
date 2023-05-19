package org.example.ui;

import org.example.dto.LoginRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateNewUser extends JDialog{
    private JTextField tf_username;
    private JTextField tf_fullname;
    private JPasswordField tf_password;
    private JTextField tf_email;
    private JTextField tf_dateofbirth;
    private JTextField tf_phone;
    private JComboBox cb_role;
    private JButton btn_create;
    private JButton btn_cancel;
    private JPanel panel_createnewuser;

    public CreateNewUser(JFrame parent) {
        super(parent);
        setTitle("Create new user");
        setContentPane(panel_createnewuser);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
