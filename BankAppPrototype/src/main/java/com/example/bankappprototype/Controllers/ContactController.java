package com.example.bankappprototype.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for handling contact form submissions.
 */
public class ContactController implements Initializable {
    public TextArea contact_textarea;
    public Button contact_send_btn;

    /**
     * Initializes the controller class.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add any required initialization code here
    }
}
