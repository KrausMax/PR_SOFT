package com.example.bankappprototype.Controllers.Admin;

import com.example.bankappprototype.Models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the Client cell in the admin view.
 */
public class ClientCellController implements Initializable {

    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;

    private final Client client;

    /**
     * Constructor for the ClientCellController.
     *
     * @param client the client object containing client details.
     */
    public ClientCellController(Client client) {
        this.client = client;
    }

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fName_lbl.textProperty().bind(client.firstNameProperty());
        lName_lbl.textProperty().bind(client.lastNameProperty());
        pAddress_lbl.textProperty().bind(client.pAddressProperty());
        ch_acc_lbl.textProperty().bind(client.checkingAccountProperty().asString());
        sv_acc_lbl.textProperty().bind(client.savingsAccountProperty().asString());
        date_lbl.textProperty().bind(client.dateProperty().asString());
    }
}
