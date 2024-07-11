package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.TransactionTypes;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for handling money transfers.
 */
public class TransferController implements Initializable {
    public TextField euro_fld;
    public Button commit_btn;
    public Label update_lbl;
    public TextArea message_fld;
    public TextField iban_fld;
    public TextField name_fld;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        euro_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^(\\d*)\\.?(\\d){0,2}$")) {
                euro_fld.setText(oldValue);
            }
        });

        commit_btn.setOnAction(event -> commitTransfer());
    }

    /**
     * Commits the transfer after validating the input fields.
     */
    private void commitTransfer() {
        if (euro_fld.getText().trim().isEmpty()) {
            update_lbl.setText("No amount given");
            return;
        } else if (message_fld.getText().trim().isEmpty()) {
            update_lbl.setText("No message given");
            return;
        } else if (name_fld.getText().trim().isEmpty()) {
            update_lbl.setText("No name given");
            return;
        }

        double amount = Double.parseDouble(euro_fld.getText());
        if (amount > Model.getInstance().getDatabaseDriver().getAccountBalance(Model.getInstance().getClient().checkingAccountProperty().getValue().accountNumberProperty().getValue())) {
            update_lbl.setText("Amount greater than current balance");
            return;
        } else if (amount > Model.getInstance().getClient().checkingAccountProperty().getValue().transactionLimitProperty().getValue()) {
            update_lbl.setText("Amount greater than transaction limit");
            return;
        }

        String iban = iban_fld.getText();
        int receiverID = -1;
        if (Model.getInstance().getDatabaseDriver().ibanValid(iban)) {
            receiverID = Model.getInstance().getDatabaseDriver().getAccountId(iban);
        } else {
            update_lbl.setText("IBAN is not valid");
            return;
        }

        String message = "To " + name_fld.getText() + ": " + message_fld.getText();

        if (Model.getInstance().getDatabaseDriver().payment(Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue(), amount, message, TransactionTypes.UEBERWEISUNG.toString(), receiverID)) {
            update_lbl.setText("Transfer successful");
        } else {
            update_lbl.setText("Transfer failed");
        }
    }
}
