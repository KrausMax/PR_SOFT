package com.example.bankappprototype.Controllers.Admin;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing the clients view in the admin section.
 */
public class ClientsController implements Initializable {
    public ListView clients_listview;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(e -> new ClientCellFactory());
    }

    /**
     * Initializes the data for the clients list view.
     */

    private void initData() {
        if(Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }
}
