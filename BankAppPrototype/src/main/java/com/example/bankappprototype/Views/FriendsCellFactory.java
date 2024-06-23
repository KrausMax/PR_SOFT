package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Client.FriendsCellController;
import com.example.bankappprototype.Controllers.Client.TransactionCellController;
import com.example.bankappprototype.Models.Friends;
import com.example.bankappprototype.Models.Transaction;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class FriendsCellFactory extends ListCell<Friends> {
    @Override
    protected void updateItem(Friends friends, boolean empty) {
        super.updateItem(friends, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/FriendsCell.fxml"));
            FriendsCellController controller = new FriendsCellController(friends);
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
