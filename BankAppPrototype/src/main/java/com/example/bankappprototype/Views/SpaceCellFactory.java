package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Client.SpaceCellController;
import com.example.bankappprototype.Models.SavingsAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * A custom cell factory for displaying savings account (space) details in a ListView.
 */
public class SpaceCellFactory extends ListCell<SavingsAccount> {

    /**
     * Updates the cell with the provided space details.
     *
     * @param space the savings account to display
     * @param empty whether the cell is empty
     */
    @Override
    protected void updateItem(SavingsAccount space, boolean empty) {
        super.updateItem(space, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/SpaceCell.fxml"));
            SpaceCellController controller = new SpaceCellController(space);
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
