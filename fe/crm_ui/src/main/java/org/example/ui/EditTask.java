package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class EditTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JRadioButton inProgressRadioButton;
    private JRadioButton doneRadioButton;
    private JButton SAVEButton;
    private JButton CANCELButton;
    private JPanel panel_edittask;

    public EditTask(JFrame parent) {
        super(parent);
        setTitle("Edit Screen");
        setContentPane(panel_edittask);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
