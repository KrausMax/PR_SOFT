package com.example.bankappprototype.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing user accounts.
 */
public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public Button trans_to_space_btn;
    public TextField amount_to_space;
    public Button trans_to_main_btn;
    public TextField amount_from_space_to_main;
    public Label sv_acc_num1;
    public Label withdrawal_limit1;
    public Label sv_acc_date1;
    public Label sv_acc_bal1;
    public TextField amount_from_spacetwo_to_main;
    public Button trans_to_main_btn1;
    public ChoiceBox space_choice_box;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic
    }
}
