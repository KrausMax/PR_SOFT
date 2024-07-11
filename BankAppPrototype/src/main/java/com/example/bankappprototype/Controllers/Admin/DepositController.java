package com.example.bankappprototype.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing deposits in the admin view.
 */
public class DepositController implements Initializable {
    public TextField pAddress_fld;
    public Button search_btn;
    public ListView result_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add initialization logic here
    }
}
