package com.example.bankappprototype.Controllers;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for handling login operations.
 */
public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label payee_address_lbl;
    public TextField payee_address_fld;
    public Label password_lbl;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public Button register_btn;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
        login_btn.setOnAction(event -> onLogin());

        // Event Handler so the User is able to press enter to log in
        payee_address_fld.setOnAction(event -> login_btn.fire());
        password_fld.setOnAction(event -> login_btn.fire());
    }

    /**
     * Handles the login operation.
     */
    private void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        String emailAddress = payee_address_fld.getText();
        String password = password_fld.getText();

        if (emailAddress.isEmpty() || password.isEmpty()) {
            error_lbl.setText("Bitte E-Mail-Adresse und Passwort eingeben!");
            return;
        }

        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            // Evaluate Client Login Credentials
            Model.getInstance().evaluateClientCred(payee_address_fld.getText(), password_fld.getText());
            if(Model.getInstance().getClientLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showClientWindow();
                //Close the Login Stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                payee_address_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Falscher Username oder Passwort");
            }
        } else {
            // Evaluate Admin Login Credentials
            Model.getInstance().evaluateAdminCred(payee_address_fld.getText(), password_fld.getText());
            if(Model.getInstance().getAdminLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showAdminWindow();
                // Close the Login Stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                payee_address_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Falscher Username oder Passwort");
            }
        }
    }
}
