package org.example.ui;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JComboBox comboBox1;
    private JRadioButton inProgressRadioButton;
    private JPanel panel_edittask;
    private JXDatePicker dp_date;
    private JRadioButton doneRadioButton;
    private JButton CREATEButton;
    private JButton CANCELButton;
    DateFormat dateFormat;
    String date;
    Date currentDate = new Date();
    public EditTask(JFrame parent) {
        super(parent);

        dp_date.setFormats("dd/MM/yyyy");
        dp_date.setDate(currentDate);

        setTitle("Edit Screen");
        setContentPane(panel_edittask);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
        setLocationRelativeTo(null);

        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = dateFormat.format(dp_date.getDate());
                System.out.println(date);

            }
        });

        setVisible(true);

    }
}
