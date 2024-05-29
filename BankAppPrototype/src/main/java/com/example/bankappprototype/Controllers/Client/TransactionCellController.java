package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {

    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label trans_type_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;
    public Label trans_message;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (transaction.senderProperty().getValue().equals(String.valueOf(Model.getInstance().getActiveAccount()))){
            if (transaction.receiverProperty().getValue().equals(String.valueOf(Model.getInstance().getActiveAccount()))){
                in_icon.setFill(Color.GRAY);
                out_icon.setFill(Color.GRAY);
            }else in_icon.setVisible(false);
        }else out_icon.setVisible(false);

        trans_date_lbl.textProperty().bind(transaction.dateProperty().asString());
        trans_type_lbl.textProperty().bind(transaction.transactionTypeProperty());

        trans_message.textProperty().bind(transaction.messageProperty());

        sender_lbl.textProperty().bind(new SimpleStringProperty(Model.getInstance().getClientEmailByAccountID(Integer.parseInt(transaction.senderProperty().getValue()))));
        receiver_lbl.textProperty().bind(new SimpleStringProperty(Model.getInstance().getClientEmailByAccountID(Integer.parseInt(transaction.receiverProperty().getValue()))));

        amount_lbl.textProperty().bind(transaction.amountProperty().asString());
    }
}
