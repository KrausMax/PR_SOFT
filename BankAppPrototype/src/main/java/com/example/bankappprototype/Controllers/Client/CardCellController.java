package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Card;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CardCellController implements Initializable {

    public Label iban_lbl;
    public Label seq_num_lbl;
    public CheckBox show_box;
    public PasswordField phidden_fld;
    public TextField pplain_fld;

    private final Card card;
    public CardCellController(Card card) {
        this.card = card;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iban_lbl.setText(card.cardNumberProperty().getValue());
        seq_num_lbl.setText(String.valueOf(card.sequenceNumberProperty().getValue()));
        phidden_fld.setText(String.valueOf(card.pinProperty().getValue()));
        show_box.setOnAction(event -> togglevisiblePin());
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
