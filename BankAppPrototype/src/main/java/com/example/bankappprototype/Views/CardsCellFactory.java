package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Client.CardCellController;
import com.example.bankappprototype.Controllers.Client.SpaceCellController;
import com.example.bankappprototype.Models.Account;
import com.example.bankappprototype.Models.Card;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class CardsCellFactory extends ListCell<Card> {
    protected void updateItem(Card card, boolean empty) {
        super.updateItem(card, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/CardCell.fxml"));
            CardCellController controller = new CardCellController(card);
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
