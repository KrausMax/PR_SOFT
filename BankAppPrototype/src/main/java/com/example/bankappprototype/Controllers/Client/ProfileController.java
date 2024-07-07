package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    public TextField fName_fld;
    public TextField lName_fld;
    public TextField email_fld;
    public Button save_profile_btn;
    public PasswordField password_fld;
    public TextField plaintxt_password_fld;
    public CheckBox show_box;
    public TextField password_change_fld;
    public Label update_lbl;
    public ImageView profile_picture_imageview;
    private File filePath;

    public void togglevisiblePassword() {
        if (show_box.isSelected()) {
            plaintxt_password_fld.setText(password_fld.getText());
            plaintxt_password_fld.setVisible(true);
            password_fld.setVisible(false);
            return;
        }
        password_fld.setVisible(true);
        plaintxt_password_fld.setVisible(false);
    }
    public void updateClientData(){
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String email = email_fld.getText();
        String password;
        if (password_change_fld.getText().equals("")) {
            password = password_fld.getText();
        }
        else {
            password = password_change_fld.getText();
        }
        Model.getInstance().getDatabaseDriver().updateClient(fName, lName, email, password);
        Model.getInstance().getClient().firstNameProperty().set(fName);
        Model.getInstance().getClient().lastNameProperty().set(lName);
        Model.getInstance().getClient().pwordProperty().set(password);
        update_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
        update_lbl.setText("Data Updated Successfully!");
        password_fld.setText(Model.getInstance().getClient().pwordProperty().get());
        plaintxt_password_fld.setText(Model.getInstance().getClient().pwordProperty().get());
        password_change_fld.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_fld.setText(Model.getInstance().getClient().pAddressProperty().get());
        fName_fld.setText(Model.getInstance().getClient().firstNameProperty().get());
        lName_fld.setText(Model.getInstance().getClient().lastNameProperty().get());
        password_fld.setText(Model.getInstance().getClient().pwordProperty().get());
        show_box.setOnAction(event -> togglevisiblePassword());
        save_profile_btn.setOnAction(event -> updateClientData());
        Image image = new Image(Model.getInstance().getClient().imageProperty().get());
        profile_picture_imageview.setImage(image);
    }

    public void chooseImageButtonPushed(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        this.filePath = fileChooser.showOpenDialog(stage);

        if (this.filePath != null) {
            Image image = new Image(filePath.toURI().toString());
            Model.getInstance().getDatabaseDriver().updateProfilePic(filePath.toURI().toString(), email_fld.getText());
            update_lbl.setText("Profile Picture Changed");
            profile_picture_imageview.setImage(image);
        }
    }
}
