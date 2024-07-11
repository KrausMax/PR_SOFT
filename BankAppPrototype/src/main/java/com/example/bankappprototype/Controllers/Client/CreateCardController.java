package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that allows the client to create a new card.
 */
public class CreateCardController implements Initializable {
    public Button create_card_btn;
    public Label created_successfully_lbl;
    public TextField limit_fld;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limit_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                limit_fld.setText(oldValue);
            }
        });
        create_card_btn.setOnAction(event -> createCard());
    }

    /**
     * Creates a new card for the client with the specified limit.
     * Displays a success or error message depending on the input validation.
     */
    private void createCard() {
        if (limit_fld.getText().isEmpty()) {
            created_successfully_lbl.setText("Please provide a limit");
            return;
        }
        int limit;
        try {
            limit = Integer.parseInt(limit_fld.getText().trim());
        } catch (NumberFormatException e) {
            created_successfully_lbl.setText("Please only input numbers");
            return;
        }

        Model.getInstance().getDatabaseDriver().createCard(limit);
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CARDS);
    }
}
