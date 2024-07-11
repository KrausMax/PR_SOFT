package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Client.FriendsCellController;
import com.example.bankappprototype.Models.Friends;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * A custom cell factory for displaying friends details in a ListView.
 */
public class FriendsCellFactory extends ListCell<Friends> {
    /**
     * Updates the cell with the provided friends details.
     *
     * @param friends the friends to display
     * @param empty whether the cell is empty
     */
    @Override
    protected void updateItem(Friends friends, boolean empty) {
        super.updateItem(friends, empty);
        if (empty) {
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
