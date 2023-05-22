package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class CreateTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JTextField tf_duedate;
    private JComboBox cb_employee;
    private JButton btn_save;
    private JButton btn_cancel;
    private JPanel panel_createtask;

    public CreateTask(JFrame parent) {
        super(parent);
        setTitle("Create Task");
        setContentPane(panel_createtask);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
