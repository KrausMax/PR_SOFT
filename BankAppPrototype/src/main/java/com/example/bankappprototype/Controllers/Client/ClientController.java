package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //AI generated Code
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal)-> {
            switch(newVal){
                case TRANSACTIONS -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                case PROFILE -> client_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                case FRIENDS -> client_parent.setCenter(Model.getInstance().getViewFactory().getFriendsView());
                case REPORT -> client_parent.setCenter(Model.getInstance().getViewFactory().getReportView());
                case CREATE_SPACE -> client_parent.setCenter(Model.getInstance().getViewFactory().getCreateSpaceView());
                case BANKOMAT -> client_parent.setCenter(Model.getInstance().getViewFactory().getBankomatView());
                case CARDS -> client_parent.setCenter(Model.getInstance().getViewFactory().getCardsView());
                case FLOWCHART -> client_parent.setCenter(Model.getInstance().getViewFactory().getFlowChartView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
