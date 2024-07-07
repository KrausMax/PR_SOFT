package com.example.bankappprototype.Controllers.Admin;

import com.example.bankappprototype.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public TextField emailAddress_fld;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;

    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(event -> createClient());
        ch_acc_box.selectedProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal) {
                createCheckingAccountFlag = true;
            }
        });
        sv_acc_box.selectedProperty().addListener((observable, oldVal, newVal) -> {
            if(newVal) {
                createSavingsAccountFlag = true;
            }
        });
    }

    private void createClient() {
        //Create Checking Account
        if(createCheckingAccountFlag) {
            createAccount("Checking");
        }

        //Create Savings Account ODER SPACES FÜR SPÄTER
        if(createSavingsAccountFlag) {
            createAccount("Savings");
        }

        // Create Client
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String emailAddress = emailAddress_fld.getText();
        String password =password_fld.getText();
        Model.getInstance().getDatabaseDriver().createClient(fName, lName, emailAddress, password, LocalDate.now());
        error_lbl.setStyle("-fx-text-fill: blue; -fx-font-size: 1.3em; -fx-font-weight: bold");
        error_lbl.setText("Client Created Successfully!");
        emptyFields();
    }

    private void createAccount(String accountType) {
        double balance = Double.parseDouble(ch_amount_fld.getText());
        // Generate Account Number
        String firstSection = "AT74 2032 0321 0222";
        String lastSection = Integer.toString((new Random()).nextInt(9000) + 1000);
        String accountNumber = firstSection + " " + lastSection;
        // Create the Checking or Savings Account
        if(accountType.equals("Checking")) {
            Model.getInstance().getDatabaseDriver().createMainAccount(accountNumber, 10, balance);
        } else {
            Model.getInstance().getDatabaseDriver().createSpace(accountNumber, 2000, balance,"My Space", "\\IdeaProjects\\PR_SOFT\\BankAppPrototype\\src\\main\\resources\\Images\\pepe.png");
        }
    }

    private void emptyFields() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        emailAddress_fld.setText("");
        ch_acc_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_acc_box.setSelected(false);
        sv_amount_fld.setText("");
    }

}
