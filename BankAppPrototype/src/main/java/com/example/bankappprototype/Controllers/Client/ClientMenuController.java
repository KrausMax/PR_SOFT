package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing the client menu options.
 */
public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button friends_btn;
    public Button create_space_btn;
    public Button bankomat_btn;
    public Button flow_chart_btn;

    /**
     * Initializes the controller class.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    /**
     * Adds listeners to the buttons to handle menu option changes.
     */
    private void addListeners() {
        dashboard_btn.setOnAction(Event -> onDashboard());
        transaction_btn.setOnAction(Event -> onTransactions());
        profile_btn.setOnAction(Event -> onProfile());
        friends_btn.setOnAction(Event -> onFriends());
        create_space_btn.setOnAction(Event -> onCreateSpace());
        logout_btn.setOnAction(Event -> onLogout());
        bankomat_btn.setOnAction(Event -> onBankomat());
        flow_chart_btn.setOnAction(Event -> onFlowChart());
    }

    /**
     * Sets the selected menu item to dashboard.
     */
    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    /**
     * Sets the selected menu item to transactions.
     */
    private void onTransactions() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    /**
     * Sets the selected menu item to profile.
     */
    private void onProfile() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.PROFILE);
    }

    /**
     * Sets the selected menu item to friends.
     */
    private void onFriends() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.FRIENDS);
    }

    /**
     * Sets the selected menu item to create space.
     */
    private void onCreateSpace() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CREATE_SPACE);
    }

    /**
     * Sets the selected menu item to bankomat.
     */
    private void onBankomat() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.BANKOMAT);
    }

    /**
     * Sets the selected menu item to flow chart.
     */
    private void onFlowChart() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.FLOWCHART);
    }

    /**
     * Logs out the client and closes the client window, showing the login window.
     */
    private void onLogout() {
        // Get Stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        // Close the client window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show login window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set c
    }
}