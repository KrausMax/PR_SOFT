package com.example.bankappprototype.Controllers.Admin;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Admin menu.
 */
public class AdminMenuController implements Initializable {
    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    /**
     * Adds event listeners to the buttons.
     */
    private void addListeners() {
        create_client_btn.setOnAction(event -> onCreateClient());
        clients_btn.setOnAction(event -> onClients());
        deposit_btn.setOnAction(event -> onDeposit());
        logout_btn.setOnAction(Event -> onLogout());
    }

    /**
     * Handles the create client button action.
     */
    private void onCreateClient() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

    /**
     * Handles the clients button action.
     */
    private void onClients() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
    }

    /**
     * Handles the deposit button action.
     */
    private void onDeposit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DEPOSIT);
    }

    /**
     * Handles the logout button action.
     */
    private void onLogout() {
        // Get Stage
        Stage stage = (Stage) clients_btn.getScene().getWindow();
        // Close the Admin Window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Admin Login Success Flag to False
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}
