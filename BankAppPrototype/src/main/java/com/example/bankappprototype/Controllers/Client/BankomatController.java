package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.TransactionTypes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Controller class for simulating an ATM machine.
 */
public class BankomatController implements Initializable {

    @FXML
    public Button cardpaybutton;

    @FXML
    public TextField cardpaysum_field;

    @FXML
    public TextField cnumber_field;

    @FXML
    public TextField fnumber_field;

    @FXML
    public TextField gnumber_field;

    @FXML
    public Button inbutton;

    @FXML
    public TextField inoutsum_field;

    @FXML
    public TextField message_field;

    @FXML
    public Button outbutton;

    @FXML
    public Label systemlabel;

    @FXML
    public Button verifybutton;

    private String accountID;
    private String message;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inoutsum_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^(\\d*)\\.?(\\d){0,2}$")) {
                inoutsum_field.setText(oldValue);
            }
        });

        cardpaysum_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^(\\d*)\\.?(\\d){0,2}$")) {
                cardpaysum_field.setText(oldValue);
            }
        });
        verifybutton.setOnAction(actionEvent -> verifyCard());
        inbutton.setOnAction(actionEvent -> inboundPayment());
        outbutton.setOnAction(actionEvent -> outboundPayment());
        cardpaybutton.setOnAction(actionEvent -> cardPayment());
    }

    /**
     * Verifies the card using the provided card number, first name, and last name.
     */
    private void verifyCard() {
        accountID = Model.getInstance().verifyCard(cnumber_field.getText(), fnumber_field.getText(), gnumber_field.getText());

        if (accountID != null) {
            systemlabel.textProperty().set("Karte verifiziert!");
        } else {
            systemlabel.textProperty().set("Karte nicht gefunden!");
        }
    }

    /**
     * Handles inbound payments.
     */
    private void inboundPayment() {
        if (accountID != null) {
            message = "Card: " + cnumber_field.getText();

            if (Model.getInstance().getDatabaseDriver().payment(1, Double.parseDouble(inoutsum_field.getText()), message, TransactionTypes.BANKOMAT_EINZAHLUNG.toString(), Integer.parseInt(accountID))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Bankomat - Einzahlung");
                alert.setContentText("Ihre Transaktion war erfolgreich, " + inoutsum_field.getText() + "€ wurden auf das Konto gutgeschrieben.");
                inoutsum_field.clear();

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Bankomat");
            alert.setHeaderText("Transaktionsfehler");
            alert.setContentText("Ihre Karte wurde noch nicht verifiziert, bitte führen Sie ihre Karte ein und probieren Sie es erneut");

            alert.showAndWait();
        }
    }

    /**
     * Handles outbound payments.
     */
    private void outboundPayment() {
        if (accountID != null) {
            message = "Card: " + cnumber_field.getText();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().getCard(cnumber_field.getText());
            int limit = -1;
            boolean terminal = false;
            try {
                limit = resultSet.getInt("CardLimit");
                terminal = resultSet.getInt("StatusTerminal") == 1;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Double.parseDouble(inoutsum_field.getText()) > limit || !terminal) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Bankomat - Abhebung");
                alert.setContentText("Abhebung abgelehnt");

                alert.showAndWait();
            } else if (Model.getInstance().getDatabaseDriver().payment(Integer.parseInt(accountID), Double.parseDouble(inoutsum_field.getText()), message, TransactionTypes.BANKOMAT_BEHEBUNG.toString(), 1)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Bankomat - Abhebung");
                alert.setContentText("Ihre Transaktion war erfolgreich, " + inoutsum_field.getText() + "€ wurden vom Konto abgehoben.");
                inoutsum_field.clear();
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Bankomat");
            alert.setHeaderText("Transaktionsfehler");
            alert.setContentText("Ihre Karte wurde noch nicht verifiziert, bitte führen Sie ihre Karte ein und probieren Sie es erneut");

            alert.showAndWait();
        }
    }

    /**
     * Handles card payments.
     */
    private void cardPayment() {
        if (accountID != null) {
            message = "Card: " + cnumber_field.getText() + " | " + message_field.getText();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().getCard(cnumber_field.getText());
            int limit = -1;
            boolean terminal = false;
            try {
                limit = resultSet.getInt("CardLimit");
                terminal = resultSet.getInt("StatusTerminal") == 1;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Double.parseDouble(cardpaysum_field.getText()) > limit || !terminal) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Transaktionsfehler");
                alert.setContentText("Kartenzahlung abgelehnt");

                alert.showAndWait();
            } else if (Model.getInstance().getDatabaseDriver().payment(Integer.parseInt(accountID), Double.parseDouble(cardpaysum_field.getText()), message, TransactionTypes.BANKOMAT_EINZAHLUNG.toString(), 2)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Kartenzahlung getaetigt");
                alert.setContentText("Ihre Transaktion war erfolgreich, " + cardpaysum_field.getText() + "€ wurden vom Konto abgehoben.");
                inoutsum_field.clear();
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Bankomat");
            alert.setHeaderText("Transaktionsfehler");
            alert.setContentText("Ihre Karte wurde noch nicht verifiziert, bitte führen Sie ihre Karte ein und probieren Sie es erneut");

            alert.showAndWait();
        }
    }
}
