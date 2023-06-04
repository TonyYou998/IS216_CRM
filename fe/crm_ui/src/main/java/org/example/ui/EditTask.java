package org.example.ui;

import org.example.dto.*;
import org.example.ui.components.ComboBoxItem;
import org.example.utils.ApiClient;
import org.jdesktop.swingx.JXDatePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    String date,status;
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
        changeFormat(getTaskResponseSelection.getEndDate());


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
                status = inProgressRadioButton.getText();
            }
        });

        doneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = doneRadioButton.getText();
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

        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callApi(token,getTaskResponse.getId());
            }
        });
        setVisible(true);

    }

    private void callApi(String token, int id) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        date = dateFormat.format(dp_date.getDate());

        CreateTaskRequest createTaskRequest = new CreateTaskRequest(tf_taskname.getText(), LocalDateTime.now().toString(),date,userId,getTaskResponse.getProjectId());
        Call<MyResponse<CreateTaskResponse>> patchUpdateTask = ApiClient.callApi().patchUpdateTask("Bearer "+token,id,createTaskRequest);
        patchUpdateTask.enqueue(new Callback<MyResponse<CreateTaskResponse>>() {
            @Override
            public void onResponse(Call<MyResponse<CreateTaskResponse>> call, Response<MyResponse<CreateTaskResponse>> response) {
                dispose();
            }

            @Override
            public void onFailure(Call<MyResponse<CreateTaskResponse>> call, Throwable throwable) {
                System.out.println("call fail");

            }
        });
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

    private void changeFormat(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(date);
            dp_date.setDate(parsedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
