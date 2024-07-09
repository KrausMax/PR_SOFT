package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Card;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller-Klasse für die einzelnen Kartenzellen in der Karten ListView
 */
public class CardCellController implements Initializable {

    public Label iban_lbl;
    public Label seq_num_lbl;
    public CheckBox show_box;
    public PasswordField phidden_fld;
    public TextField pplain_fld;
    public Button edit_card_btn;

    private final Card card;

    /**
     * Konstruktor für CardCellController
     * @param card Das Karten Objekt mit den Daten der Karte die abgebildet werden sollen
     */
    public CardCellController(Card card) {
        this.card = card;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iban_lbl.setText(card.cardNumberProperty().getValue());
        seq_num_lbl.setText(String.valueOf(card.sequenceNumberProperty().getValue()));
        phidden_fld.setText(String.valueOf(card.pinProperty().getValue()));
        show_box.setOnAction(event -> togglevisiblePin());
        edit_card_btn.setOnAction(event -> editCard());
    }

    private void editCard() {
        Model.getInstance().setCardNum(card.cardNumberProperty().getValue());
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.EDIT_CARD);
    }

    private void togglevisiblePin() {
        if (show_box.isSelected()) {
            pplain_fld.setText(phidden_fld.getText());
            pplain_fld.setVisible(true);
            phidden_fld.setVisible(false);
            return;
        }
        phidden_fld.setVisible(true);
        pplain_fld.setVisible(false);
    }
}
