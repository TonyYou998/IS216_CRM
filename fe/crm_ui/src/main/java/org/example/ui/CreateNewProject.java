package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class CreateNewProject extends JDialog {
    private JTextField tf_pjname;
    private JTextField tf_description;
    private JTextField tf_duedate;
    private JComboBox cb_leader;
    private JButton btn_create;
    private JButton btn_cancel;
    private JPanel panel_createnewpj;

    public CreateNewProject(JFrame parent) {
        super(parent);
        setTitle("Create new project");
        setContentPane(panel_createnewpj);
        setMinimumSize(new Dimension(500,300));
        setModal(true);
        setLocationRelativeTo(null);

        setVisible(true);

    }
}
