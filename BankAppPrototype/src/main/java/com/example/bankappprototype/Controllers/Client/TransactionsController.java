package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.TransactionTypes;
import com.example.bankappprototype.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_listview;

    public ToolBar account_bar;

    public ComboBox filter_box;

    public TextField search_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        transactions_listview.setItems(Model.getInstance().getTransactions());
        transactions_listview.setCellFactory(e -> new TransactionCellFactory());

        filter_box.getItems().add("");
        filter_box.getItems().addAll(TransactionTypes.values());
        filter_box.getSelectionModel().select(0);
        filter_box.setOnAction(actionEvent -> transactions_listview.setItems(Model.getInstance().getTransactions().filtered(transaction -> transaction.transactionTypeProperty().getValue().contains(filter_box.getValue().toString())).sorted()));

        search_field.setOnAction(actionEvent -> transactions_listview.setItems(Model.getInstance().getTransactions().filtered(transaction -> transaction.messageProperty().getValue().contains(search_field.getText().toString()) && transaction.transactionTypeProperty().getValue().contains(filter_box.getValue().toString()))));

        ArrayList<Button> buttons = new ArrayList<>();
        Button button1 = new Button("Checking");
        Button button2 = new Button("Savings");
        buttons.add(button1);
        buttons.add(button2);
        button1.setOnAction(e-> setCurrentTransactions(Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue()));
        button2.setOnAction(e->setCurrentTransactions(Model.getInstance().getClient().savingsAccountProperty().getValue().idProperty().getValue()));
        account_bar.getItems().addAll(buttons);
    }

    private void initData() {
        setCurrentTransactions(Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue());
    }

    private void setCurrentTransactions(int accountID){
        Model.getInstance().setActiveAccount(accountID);
        Model.getInstance().setTransactions(accountID);
    }
}
