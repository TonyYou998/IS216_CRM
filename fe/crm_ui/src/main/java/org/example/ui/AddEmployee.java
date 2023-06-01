package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class AddEmployee extends JDialog {
    private JButton btn_save;
    private JButton btn_cancel;
    private JPanel panel_addemployee;
    private JComboBox cb_username;

    public AddEmployee(JFrame parent,String token) {
        super(parent);
        setTitle("Edit Screen");
        setContentPane(panel_addemployee);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
