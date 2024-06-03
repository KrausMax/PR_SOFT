package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Account;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.SpaceCellFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label income_lbl;
    public Label expense_lbl;
    public TextField payee_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Label space_lbl;
    public TextField name_fld;
    public Label space_lbl1;
    public ListView space_listview;
    public Label mainAccNum_lbl;
    public Button showCards_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Account mainAcc = Model.getInstance().getClient().checkingAccountProperty().getValue();
        checking_bal.setText(""+ mainAcc.balanceProperty().get());
        mainAccNum_lbl.setText(mainAcc.accountNumberProperty().get());

        initSpaceData();
        space_listview.setItems(Model.getInstance().getSpaces());
        space_listview.setCellFactory(e -> new SpaceCellFactory());

        showCards_btn.setOnAction(event -> transferDialogue());
    }

    private void initSpaceData() {
        setCurrentSpaces(Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue());
    }
    private void setCurrentSpaces(int accountID){
        Model.getInstance().setActiveAccount(accountID);
        Model.getInstance().setSpaces(accountID);
    }

    public void transferDialogue() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/Client/TransferDialogue.fxml"));
            Pane transferDialogPane =fxmlLoader.load();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
