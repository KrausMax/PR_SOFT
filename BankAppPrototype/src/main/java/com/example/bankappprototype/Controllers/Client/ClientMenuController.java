package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;
    public Button friends_btn;
    public Button create_space_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboard_btn.setOnAction(Event -> onDashboard());
        transaction_btn.setOnAction(Event -> onTransactions());
        accounts_btn.setOnAction(Event -> onAccounts());
        profile_btn.setOnAction(Event -> onProfile());
        friends_btn.setOnAction(Event -> onFriends());
        report_btn.setOnAction(Event -> onReport());
        create_space_btn.setOnAction(Event -> onCreateSpace());
        logout_btn.setOnAction(Event -> onLogout());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }

    private void onProfile() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.PROFILE);
    }

    private void onFriends() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.FRIENDS);
    }

    private void onReport() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.REPORT);
    }

    private void onCreateSpace() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CREATE_SPACE);
    }



    private void onLogout() {
        // Get Stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        // Close the client Window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Client Login Success Flag to False
        Model.getInstance().setClientLoginSuccessFlag(false);
    }
}
