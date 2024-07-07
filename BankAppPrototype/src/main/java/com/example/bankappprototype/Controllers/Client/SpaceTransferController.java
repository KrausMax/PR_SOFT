package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.TransactionTypes;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SpaceTransferController implements Initializable {
    public TextField euro_fld;
    public Button commit_btn;
    public Label update_lbl;
    public TextArea message_fld;
    public RadioButton plus_rd;
    public RadioButton minus_rd;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        euro_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("^(\\d*)\\.?(\\d){0,2}$")){
                euro_fld.setText(oldValue);
            }
        });

        ToggleGroup group = new ToggleGroup();

        plus_rd.setToggleGroup(group);
        minus_rd.setToggleGroup(group);

        commit_btn.setOnAction(event -> commitTransfer());
    }

    private void commitTransfer() {
        if (euro_fld.getText().trim().isEmpty()) {
            update_lbl.setText("No amount given");
            return;
        } else if (message_fld.getText().trim().isEmpty()) {
            update_lbl.setText("No message given");
            return;
        }
        double amount = Double.parseDouble(euro_fld.getText());
        if (minus_rd.isSelected() && amount > Model.getInstance().getClient().savingsAccountProperty().getValue().balanceProperty().getValue()) {
            update_lbl.setText("Amount greater than current balance");
            return;
        } else if (amount > Model.getInstance().getClient().savingsAccountProperty().getValue().withdrawalLimitProperty().getValue()) {
            update_lbl.setText("Amount greater than transaction limit");
            return;
        } else if (plus_rd.isSelected() && amount > Model.getInstance().getClient().checkingAccountProperty().getValue().balanceProperty().getValue()) {
            update_lbl.setText("Amount greater than Main Account balance");
            return;
        }

        String message = message_fld.getText();
        int sender;
        int receiver;

        if (plus_rd.isSelected()) {
            sender = Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue();
            receiver = Model.getInstance().getClient().savingsAccountProperty().getValue().idProperty().getValue();
        } else {
            receiver = Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue();
            sender = Model.getInstance().getClient().savingsAccountProperty().getValue().idProperty().getValue();
        }

        if (Model.getInstance().getDatabaseDriver().payment(sender, amount, message, TransactionTypes.SPACE_TRANSFER.toString(), receiver)) {
            update_lbl.setText("Transfer successful");
        } else {
            update_lbl.setText("Transfer failed");
        }
    }
}

