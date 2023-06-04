package org.example.ui;

import org.example.dto.GetAllUserAccountResponse;
import org.example.dto.GetTaskResponse;
import org.example.ui.components.ComboBoxItem;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditTask extends JDialog {
    private JTextField tf_taskname;
    private JTextField tf_description;
    private JComboBox comboBox1;
    private JPanel panel_edittask;
    private JXDatePicker dp_date;
    private JRadioButton doneRadioButton;
    private JRadioButton inProgressRadioButton;
    private JButton SAVEButton;
    private JButton CREATEButton;
    private JButton CANCELButton;
    DateFormat dateFormat;
    String date;
    private String userId="-1";
    private List<GetAllUserAccountResponse> lstEmployee;
    GetTaskResponse getTaskResponse;

    public EditTask(JFrame parent, GetTaskResponse getTaskResponseSelection, String token) {
        super(parent);

        dateFormat =new SimpleDateFormat("dd/MM/yyyy");
        dp_date.setFormats(dateFormat);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(inProgressRadioButton);
        buttonGroup.add(doneRadioButton);

        setTitle("Edit Task");
        setContentPane(panel_edittask);
        setMinimumSize(new Dimension(500,300));
        setModal(true);
        setLocationRelativeTo(null);

        lstEmployee = TaskScreen.lstAllEmployee;
        setEmployee(lstEmployee);

        getTaskResponse = getTaskResponseSelection;

        tf_taskname.setText(getTaskResponseSelection.getTaskName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(getTaskResponseSelection.getEndDate());
            dp_date.setDate(parsedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                date = dateFormat.format(dp_date.getDate());
                System.out.println(date);

            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        inProgressRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTaskResponse.setStatus(inProgressRadioButton.getText());
            }
        });

        doneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTaskResponse.setStatus(doneRadioButton.getText());
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboBoxItem selectedLeader = (ComboBoxItem) comboBox1.getSelectedItem();
                userId  = selectedLeader.getId();

                // Use the leaderId and other values as needed
                System.out.println("Selected employee: " + userId);
            }
        });
        setVisible(true);

    }

    public void setEmployee(List<GetAllUserAccountResponse> lstEmployee){
        comboBox1.addItem(new ComboBoxItem("none","-1"));
        for(GetAllUserAccountResponse item:lstEmployee){
            ComboBoxItem comboBoxItem=new ComboBoxItem(item.getUsername(),String.valueOf(item.getId()));
            comboBox1.addItem(comboBoxItem);
        }
        comboBox1.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                // Display only the username in the JComboBox
                return super.getListCellRendererComponent(list, ((ComboBoxItem) value).getDisplayText(), index,
                        isSelected, cellHasFocus);
            }
        });
    }
}
