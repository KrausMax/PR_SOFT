package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Card;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for individual card cells in the card ListView.
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
     * Constructor for CardCellController.
     *
     * @param card The Card object with the card data to be displayed.
     */
    public CardCellController(Card card) {
        this.card = card;
    }

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iban_lbl.setText(card.cardNumberProperty().getValue());
        seq_num_lbl.setText(String.valueOf(card.sequenceNumberProperty().getValue()));
        phidden_fld.setText(String.valueOf(card.pinProperty().getValue()));
        show_box.setOnAction(event -> toggleVisiblePin());
        edit_card_btn.setOnAction(event -> editCard());
    }

    /**
     * Sets the card number and switches the view to the edit card menu.
     */
    private void editCard() {
        Model.getInstance().setCardNum(card.cardNumberProperty().getValue());
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.EDIT_CARD);
    }

    /**
     * Toggles the visibility of the card PIN.
     */
    private void toggleVisiblePin() {
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
