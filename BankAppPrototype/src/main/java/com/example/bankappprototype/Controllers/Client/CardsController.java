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
 * Kontroller Klasse zum Verwalten und Abbilden der Karten des Kontos
 */
public class CardsController implements Initializable {

    public ListView cards_listview;
    public Label iban_lbl;
    public Button order_card_btn;

    private final String iban;

    /**
     * Konstruktor für CardsController
     * @param iban Die IBAN des Kontos dessen Karten angezeigt werden sollen
     */
    public CardsController(String iban) {
        this.iban = iban;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iban_lbl.setText(iban);
        initCardData();
        cards_listview.setItems(Model.getInstance().getCards());
        cards_listview.setCellFactory(e -> new CardsCellFactory());
        order_card_btn.setOnAction(Event -> orderCards());
    }

    private void orderCards() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CREATE_CARD);
    }

    private void initCardData() {
        setCurrentCards(iban);
    }

    private void setCurrentCards(String iban) {
        Model.getInstance().setCards(iban);
    }
}
