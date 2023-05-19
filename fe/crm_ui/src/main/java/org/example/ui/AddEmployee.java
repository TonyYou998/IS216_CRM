package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class AddEmployee extends JDialog {
    private JTextField tf_userid;
    private JTextField tf_projectid;
    private JButton btn_save;
    private JButton btn_cancel;
    private JPanel panel_addemployee;

    public AddEmployee(JFrame parent) {
        super(parent);
        setTitle("Edit Screen");
        setContentPane(panel_addemployee);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
