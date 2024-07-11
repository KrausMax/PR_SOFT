package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Admin.ClientCellController;
import com.example.bankappprototype.Models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * A custom cell factory for displaying client details in a ListView.
 */
public class ClientCellFactory extends ListCell<Client> {
    /**
     * Updates the cell with the provided client details.
     *
     * @param client the client to display
     * @param empty whether the cell is empty
     */
    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ClientCell.fxml"));
            ClientCellController controller = new ClientCellController(client);
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
