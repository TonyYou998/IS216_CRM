package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class AdminScreen extends JDialog {

    private JPanel panel_admin_screen;

    public AdminScreen(JFrame parent,String token) {
        super(parent);
        setTitle("AdminScreen");
        setContentPane(panel_admin_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);

        System.out.println(token);
    }

}
