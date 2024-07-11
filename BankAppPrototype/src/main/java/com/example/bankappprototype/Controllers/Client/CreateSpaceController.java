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

/**
 * Controller class that allows the client to create a new space.
 */
public class CreateSpaceController implements Initializable {

    public Button create_space_btn;
    public TextField space_name_fld;
    public ImageView space_picture_imageview;
    public Label created_successfully_lbl;
    private File filePath;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_space_btn.setOnAction(event -> createSpace());
    }

    /**
     * Allows the user to select an image to be displayed for the space.
     *
     * @param event The action event triggered by the button click.
     */
    public void chooseImageButtonPushed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        this.filePath = fileChooser.showOpenDialog(stage);

        if (this.filePath != null) {
            // Load the image directly into the ImageView
            Image image = new Image(filePath.toURI().toString());
            space_picture_imageview.setImage(image);
        }
    }

    /**
     * Creates a new space for the existing client.
     * Displays a success or error message depending on the input validation.
     */
    private void createSpace() {
        String spaceName = space_name_fld.getText().trim();
        if (spaceName.isEmpty() || filePath == null) {
            created_successfully_lbl.setText("Please provide a space name and upload an image.");
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
        Model.getInstance().setSpaces(Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue());
        created_successfully_lbl.setText("Space created successfully!");
    }
}
