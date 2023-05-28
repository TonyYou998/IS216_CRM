package org.example.ui;

import org.example.dto.GetLeaderResponse;
import org.example.dto.MyResponse;
import org.example.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CreateNewProject extends JDialog {
    private JTextField tf_pjname;
    private JTextField tf_description;
    private JTextField tf_duedate;
    private JComboBox cb_leader;
    private JButton btn_create;
    private JButton btn_cancel;
    private JPanel panel_createnewpj;
    private List<GetLeaderResponse> lstLeader;
    private String token;
    public CreateNewProject(JFrame parent, String token) {
        super(parent);

        setTitle("Create new project");
        this.token=token;
        lstLeader= callApiGetAllLeader(token);
        setContentPane(panel_createnewpj);
        setMinimumSize(new Dimension(550,350));
        setModal(true);
        setLocationRelativeTo(null);



        setVisible(true);

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
