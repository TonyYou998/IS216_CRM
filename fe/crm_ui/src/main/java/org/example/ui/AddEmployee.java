package org.example.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployee extends JDialog {
    private JPanel panel_addemployee;
    private JComboBox cb_username;
    private JButton CANCELButton;
    private JButton SAVEButton;

    public AddEmployee(JFrame parent,String token) {
        super(parent);
        setTitle("Add Employee");
        setContentPane(panel_addemployee);
        setMinimumSize(new Dimension(500,300));
        setModal(true);
        setLocationRelativeTo(null);

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);

    }
}
