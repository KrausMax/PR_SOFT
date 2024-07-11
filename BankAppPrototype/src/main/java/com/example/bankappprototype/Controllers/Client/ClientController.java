package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing the client UI, handles switching between different views.
 */
public class ClientController implements Initializable {
    public BorderPane client_parent;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TRANSACTIONS -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                case PROFILE -> client_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                case FRIENDS -> client_parent.setCenter(Model.getInstance().getViewFactory().getFriendsView());
                case REPORT -> client_parent.setCenter(Model.getInstance().getViewFactory().getReportView());
                case CREATE_SPACE -> client_parent.setCenter(Model.getInstance().getViewFactory().getCreateSpaceView());
                case BANKOMAT -> client_parent.setCenter(Model.getInstance().getViewFactory().getBankomatView());
                case CARDS -> client_parent.setCenter(Model.getInstance().getViewFactory().getCardsView());
                case FLOWCHART -> client_parent.setCenter(Model.getInstance().getViewFactory().getFlowChartView());
                case CREATE_CARD -> client_parent.setCenter(Model.getInstance().getViewFactory().getCreateCardView());
                case EDIT_CARD -> client_parent.setCenter(Model.getInstance().getViewFactory().getEditCardView());
                case TRANSFER -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransferView());
                case SPACE_TRANSFER -> client_parent.setCenter(Model.getInstance().getViewFactory().getSpaceTransferView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
