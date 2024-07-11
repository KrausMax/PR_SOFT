package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Controller class that allows the client to edit the selected card in terms of transaction limit and enable or disable card usage online or at ATMs.
 */
public class EditCardController implements Initializable {
    public TextField limit_fld;
    public Button save_card_btn;
    public ImageView picture_imageview;
    public Label update_lbl;
    public CheckBox online_box;
    public CheckBox atm_box;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().getCard(Model.getInstance().getCardNum());
        int limit = -1;
        boolean online = false;
        boolean terminal = false;
        try {
            limit = resultSet.getInt("CardLimit");
            online = resultSet.getInt("StatusOnline") == 1;
            terminal = resultSet.getInt("StatusTerminal") == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        limit_fld.setText("" + limit);
        online_box.setSelected(online);
        atm_box.setSelected(terminal);
        boolean finalOnline = online;
        boolean finalTerminal = terminal;
        save_card_btn.setOnAction(event -> saveCard(finalOnline, finalTerminal));
        limit_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                limit_fld.setText(oldValue);
            }
        });
    }

    /**
     * Saves the updated card information including the transaction limit, online status, and terminal status.
     *
     * @param prevOnline    The previous online status of the card.
     * @param prevTerminal  The previous terminal status of the card.
     */
    public void saveCard(boolean prevOnline, boolean prevTerminal) {
        int online = -1;
        int terminal = -1;
        int limit = -1;

        if (online_box.isSelected()) {
            online = 1;
        } else {
            online = 0;
        }

        if (prevOnline == online_box.isSelected()) {
            prevOnline = false;
        } else {
            prevOnline = true;
        }

        if (prevTerminal == atm_box.isSelected()) {
            prevTerminal = false;
        } else {
            prevTerminal = true;
        }

        if (atm_box.isSelected()) {
            terminal = 1;
        } else {
            terminal = 0;
        }

        if (limit_fld.getText().trim().isEmpty()) {
            limit = -1;
        } else {
            limit = Integer.parseInt(limit_fld.getText());
        }

        Model.getInstance().getDatabaseDriver().updateCard(limit, online, terminal, prevOnline, prevTerminal);
        update_lbl.setText("Update successful");
    }
}
