package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;

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

import java.util.Random;
import java.util.ResourceBundle;

public class CreateSpaceController implements Initializable {


    public Button create_space_btn;
    public TextField space_name_fld;
    public ImageView space_picture_imageview;
    public Label created_successfully_btn;
    private FileChooser fileChooser;
    private File filePath;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_space_btn.setOnAction(event -> createSpace());
    }

    /**
     * This Method will allow the user to change the image on the screen
     */
    public void chooseImageButtonPushed(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        this.filePath = fileChooser.showOpenDialog(stage);

        if (this.filePath != null) {
            // Load the image directly into the ImageView
            Image image = new Image(filePath.toURI().toString());
            space_picture_imageview.setImage(image);
        }
    }


    /**
     * This Method will create a Space for an existing user
     */

    private void createSpace() {
        String spaceName = space_name_fld.getText().trim();
        if (spaceName.isEmpty() || filePath == null) {
            created_successfully_btn.setText("Please provide a space name and upload an image.");
            return;
        }

        double transactionLimit = 2000; // Example transaction limit
        double balance = 0; // Example initial balance
        String spaceImage = filePath.getPath(); // Example path to the image file

        // Generate Account Number
        String firstSection = "AT74 2032 0321 0222";
        String lastSection = Integer.toString((new Random()).nextInt(9000) + 1000);
        String accountNumber = firstSection + " " + lastSection;

        // Call the createSpace method of the DatabaseDriver instance
        Model.getInstance().getDatabaseDriver().createSpace(accountNumber, transactionLimit, balance, spaceName, spaceImage);

        // Show success message
        created_successfully_btn.setText("Space created successfully!");
    }

}