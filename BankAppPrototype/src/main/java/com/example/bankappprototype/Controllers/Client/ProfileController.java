package com.example.bankappprototype.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    public TextField username_fld;
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField email_fld;
    public Button save_profile_btn;
    public ImageView picture_imageview;
    public PasswordField password_fld;
    public Button show_btn;
    public TextField password_change_fld;
    public Button save_pw_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
