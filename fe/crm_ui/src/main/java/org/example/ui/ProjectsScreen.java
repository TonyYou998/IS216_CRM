package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class ProjectsScreen extends JDialog {

    private JPanel panel_projects_screen;

    public ProjectsScreen(JFrame parent) {
        super(parent);
        setTitle("AdminScreen");
        setContentPane(panel_projects_screen);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
