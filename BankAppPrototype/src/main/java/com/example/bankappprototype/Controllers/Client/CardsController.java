package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.CardsCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardsController implements Initializable {

    public ListView cards_listview;
    public Label iban_lbl;

    private final String iban;
    public CardsController(String iban) {
        this.iban = iban;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iban_lbl.setText(iban);
        initCardData();
        cards_listview.setItems(Model.getInstance().getCards());
        cards_listview.setCellFactory(e -> new CardsCellFactory());
    }

    private void initCardData() {
        setCurrentCards(iban);
    }

    private void setCurrentCards(String iban) {
        Model.getInstance().setCards(iban);
    }
}
