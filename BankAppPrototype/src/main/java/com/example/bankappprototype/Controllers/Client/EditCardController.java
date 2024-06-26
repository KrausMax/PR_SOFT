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

public class EditCardController implements Initializable {
    public TextField limit_fld;
    public Button save_card_btn;
    public ImageView picture_imageview;
    public Label update_lbl;
    public CheckBox online_box;
    public CheckBox atm_box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
        save_card_btn.setOnAction(event -> saveCard());
        limit_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                limit_fld.setText(oldValue);
            }
        });
    }

    public void saveCard(){
        int online = -1;
        int terminal = -1;
        int limit = -1;

        if(online_box.isSelected()) {
            online = 1;
        } else {
            online = 0;
        }

        if (atm_box.isSelected()) {
            terminal = 1;
        } else {
            terminal = 0;
        }

        limit = Integer.parseInt(limit_fld.getText());

        Model.getInstance().getDatabaseDriver().updateCard(limit, online, terminal);
        update_lbl.setText("Update successful");
    }
}
