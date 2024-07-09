package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCardController implements Initializable {
    public Button create_card_btn;
    public Label created_successfully_btn;
    public TextField limit_fld;
    public ImageView card_picture_imageview;
    private File filePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limit_fld.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                limit_fld.setText(oldValue);
            }
        });
        create_card_btn.setOnAction(event -> createCard());
    }

    private void createCard() {
        if (limit_fld.getText().isEmpty()) {
            created_successfully_btn.setText("Please provide a limit");
            return;
        }
        int limit;
        try {
            limit = Integer.parseInt(limit_fld.getText().trim());
        } catch (NumberFormatException e) {
            created_successfully_btn.setText("Please only input numbers");
            return;
        }

        Model.getInstance().getDatabaseDriver().createCard(limit);
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CARDS);
    }
}
