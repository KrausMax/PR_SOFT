package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Client.TransactionCellController;
import com.example.bankappprototype.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * A custom cell factory for displaying transaction details in a ListView.
 */
public class TransactionCellFactory extends ListCell<Transaction> {

    /**
     * Updates the cell with the provided transaction details.
     *
     * @param transaction the transaction to display
     * @param empty whether the cell is empty
     */
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/TransactionCell.fxml"));
            TransactionCellController controller = new TransactionCellController(transaction);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
