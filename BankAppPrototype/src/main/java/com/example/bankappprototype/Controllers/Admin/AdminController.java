package com.example.bankappprototype.Controllers.Admin;

import com.example.bankappprototype.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Admin view.
 */
public class AdminController implements Initializable {
    public BorderPane admin_parent;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldVal, newVal) -> {
            switch (newVal) {
                case CLIENTS -> admin_parent.setCenter(Model.getInstance().getViewFactory().getClientsView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        });
    }
}
