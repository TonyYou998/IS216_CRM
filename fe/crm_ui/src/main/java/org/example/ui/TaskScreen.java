package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class TaskScreen extends JDialog {
    private JLabel lb_employee;
    private JLabel lb_alltask;
    private JLabel lb_backlog;
    private JLabel lb_inpro;
    private JLabel lb_done;
    private JLabel lb_overdue;
    private JLabel lb_back;
    private JLabel lb_sttbaclogs;
    private JLabel lb_sttinprogress;
    private JLabel lb_sttdone;
    private JLabel lb_sttoverdue;
    private JTable table1;
    private JPanel panel_taskscreen;

    public TaskScreen(JFrame parent) {
        super(parent);
        setTitle("Task Screen");
        setContentPane(panel_taskscreen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
