package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Client.SpaceCellController;
import com.example.bankappprototype.Models.SavingsAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class SpaceCellFactory extends ListCell<SavingsAccount> {
    protected void updateItem(SavingsAccount space, boolean empty) {
        super.updateItem(space, empty);
        if(empty) {
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
