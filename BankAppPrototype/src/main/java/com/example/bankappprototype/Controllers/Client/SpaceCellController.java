package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Account;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SpaceCellController implements Initializable {
    public Label account_number_lbl;
    public Label balance_lbl;
    public AnchorPane space_pane;
    public Button showCards_btn;

    private final Account space;
    public SpaceCellController(Account space){
        this.space = space;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account_number_lbl.setText(space.accountNumberProperty().getValue());
        balance_lbl.setText(String.valueOf(space.balanceProperty().getValue()));

        showCards_btn.setOnAction(Event -> showCards());
    }

    private void showCards() {
        Model.getInstance().setCardIban(account_number_lbl.getText());
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CARDS);
    }
}
