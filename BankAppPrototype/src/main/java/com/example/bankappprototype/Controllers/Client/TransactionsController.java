package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.SavingsAccount;
import com.example.bankappprototype.Models.TransactionTypes;
import com.example.bankappprototype.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

/**
 * Controller class for managing the transactions view, allowing users to filter and search through their transactions.
 */
public class TransactionsController implements Initializable {
    public ListView transactions_listview;
    public ComboBox filter_box;
    public TextField search_field;
    public ComboBox account_box;

    private List<String> spaceNums = new ArrayList<>();
    private Iterator<SavingsAccount> spaces;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        transactions_listview.setItems(Model.getInstance().getTransactions());
        transactions_listview.setCellFactory(e -> new TransactionCellFactory());
        spaces = Model.getInstance().getSpaces().iterator();
        while (spaces.hasNext()) {
            spaceNums.add(spaces.next().accountNumberProperty().getValue());
        }

        filter_box.getItems().add("");
        filter_box.getItems().addAll(TransactionTypes.values());
        filter_box.getSelectionModel().select(0);
        filter_box.setOnAction(actionEvent -> transactions_listview.setItems(Model.getInstance().getTransactions().filtered(transaction -> transaction.transactionTypeProperty().getValue().contains(filter_box.getValue().toString())).sorted()));

        account_box.getItems().add(Model.getInstance().getClient().checkingAccountProperty().getValue().accountNumberProperty().getValue());
        account_box.getItems().addAll(spaceNums);
        account_box.setOnAction(actionEvent -> setCurrentTransactions(account_box.getValue().toString()));

        search_field.setOnAction(actionEvent -> transactions_listview.setItems(Model.getInstance().getTransactions().filtered(transaction -> transaction.messageProperty().getValue().contains(search_field.getText().toString()) && transaction.transactionTypeProperty().getValue().contains(filter_box.getValue().toString()))));
    }

    /**
     * Initializes the transaction data for the currently selected account.
     */
    private void initData() {
        setCurrentTransactions(Model.getInstance().getClient().checkingAccountProperty().getValue().accountNumberProperty().getValue());
    }

    /**
     * Sets the current transactions for the selected account number.
     */
    private void setCurrentTransactions(String accountNum) {
        spaces = Model.getInstance().getSpaces().iterator();
        int accountID = -1;
        if (!accountNum.equals(Model.getInstance().getClient().checkingAccountProperty().getValue().accountNumberProperty().getValue())) {
            String currentNum = "";
            while (!accountNum.equals(currentNum)) {
                SavingsAccount space = spaces.next();
                currentNum = space.accountNumberProperty().getValue();
                accountID = space.idProperty().getValue();
            }
        } else {
            accountID = Model.getInstance().getClient().idProperty().getValue();
        }
        Model.getInstance().setActiveAccount(accountID);
        Model.getInstance().setTransactions(accountID);
    }
}
