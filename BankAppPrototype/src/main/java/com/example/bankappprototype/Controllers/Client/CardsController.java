package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.CardsCellFactory;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing and displaying the cards of an account.
 */
public class CardsController implements Initializable {

    public ListView cards_listview;
    public Label iban_lbl;
    public Button order_card_btn;

    private final String iban;

    /**
     * Constructor for CardsController.
     *
     * @param iban The IBAN of the account whose cards are to be displayed.
     */
    public CardsController(String iban) {
        this.iban = iban;
    }

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iban_lbl.setText(iban);
        initCardData();
        cards_listview.setItems(Model.getInstance().getCards());
        cards_listview.setCellFactory(e -> new CardsCellFactory());
        order_card_btn.setOnAction(Event -> orderCards());
    }

    /**
     * Navigates to the create card menu.
     */
    private void orderCards() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CREATE_CARD);
    }

    /**
     * Initializes card data.
     */
    private void initCardData() {
        setCurrentCards(iban);
    }

    /**
     * Sets the current cards for the given IBAN.
     *
     * @param iban The IBAN of the account whose cards are to be set.
     */
    private void setCurrentCards(String iban) {
        Model.getInstance().setCards(iban);
    }
}
