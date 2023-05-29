package org.example.ui;

import org.example.dto.GetLeaderResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import org.jdesktop.swingx.JXDatePicker;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class CreateNewProject extends JDialog {
    private JTextField tf_pjname;
    private JTextField tf_description;
    private JComboBox cb_leader;
    private JButton btn_create;
    private JButton btn_cancel;
    private JPanel panel_createnewpj;
    private JXDatePicker dp_date;

    DateFormat dateFormat;
    String date;
    Date currentDate = new Date();
    private List<GetLeaderResponse> lstLeader;
    public CreateNewProject(JFrame parent, String token) {
        super(parent);

        setTitle("Create new project");
        dp_date.setFormats("dd/MM/yyyy");
        dp_date.setDate(currentDate);

        lstLeader = callApiGetAllLeader(token);
        setLeader(lstLeader);
        setContentPane(panel_createnewpj);
        setMinimumSize(new Dimension(550, 350));
        setModal(true);
        setLocationRelativeTo(null);
        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = dateFormat.format(dp_date.getDate());
                System.out.println(date);

            }
        });


        setVisible(true);

    }


        public void setLeader(List<GetLeaderResponse> lstLeader){
            for(GetLeaderResponse item:lstLeader){
               cb_leader.addItem(item.getUsername());
            }
        }

        public List<GetLeaderResponse> callApiGetAllLeader(String token){
            Call<MyResponse<List<GetLeaderResponse>>> call = ApiClient.callApi().getLeaders("Bearer "+token);
            try{
                Response<MyResponse<List<GetLeaderResponse>>> response= call.execute();
                if(response.isSuccessful()){

                    MyResponse<List<GetLeaderResponse>> lstLeaders= response.body();
                    return lstLeaders.getContent();
                }


            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

}
