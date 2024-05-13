package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        transactions_listview.setItems(Model.getInstance().getTransactions());
        transactions_listview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initData() {
        if(Model.getInstance().getTransactions().isEmpty()) {
            Model.getInstance().setTransactions();
        }
    }
}
