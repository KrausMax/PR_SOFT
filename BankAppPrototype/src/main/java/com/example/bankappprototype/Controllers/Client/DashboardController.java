package com.example.bankappprototype.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Label space_lbl;
    public TextField name_fld;
    public Label space_lbl1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
