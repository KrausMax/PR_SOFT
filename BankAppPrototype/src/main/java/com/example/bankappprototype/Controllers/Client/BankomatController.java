package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        verifybutton.setOnAction(actionEvent -> verifyCard());
        inbutton.setOnAction(actionEvent -> inboundPayment());
        outbutton.setOnAction(actionEvent -> outboundPayment());
        cardpaybutton.setOnAction(actionEvent -> cardPayment());
    }

    private void verifyCard(){
        accountID = Model.getInstance().verifyCard(cnumber_field.getText(),fnumber_field.getText(),gnumber_field.getText());

        if (accountID!=null){
            systemlabel.textProperty().set("Karte verifiziert!");
        }else {
            systemlabel.textProperty().set("Karte nicht gefunden!");
        }
    }

    private void inboundPayment() {
        if (accountID!=null){
            if (Model.getInstance().addToCard(accountID,inoutsum_field.getText())){
                inoutsum_field.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Bankomat - Einzahlung");
                alert.setContentText("Ihre Transaktion war erfolgreich, "+inoutsum_field.getText()+"€ wurden auf das Konto gutgeschrieben.");

                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Bankomat");
            alert.setHeaderText("Transaktionsfehler");
            alert.setContentText("Ihre Karte wurde noch nicht verifiziert, bitte führen Sie ihre Karte ein und probieren Sie es erneut");

            alert.showAndWait();
        }
    }

    private void outboundPayment() {
        if (accountID!=null){
            if (Model.getInstance().subtractFromCard(accountID,inoutsum_field.getText())){
                inoutsum_field.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Bankomat");
                alert.setHeaderText("Bankomat - Abhebung");
                alert.setContentText("Ihre Transaktion war erfolgreich, "+inoutsum_field.getText()+"€ wurden vom Konto abgehoben.");

                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Bankomat");
            alert.setHeaderText("Transaktionsfehler");
            alert.setContentText("Ihre Karte wurde noch nicht verifiziert, bitte führen Sie ihre Karte ein und probieren Sie es erneut");

            alert.showAndWait();
        }
    }

    private void cardPayment() {
        if (accountID!=null){
            if (Model.getInstance().payWithCard(accountID,cardpaysum_field.getText(),message_field.getText())){
                cardpaysum_field.clear();
                message_field.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Kartenzahlung");
                alert.setHeaderText("Kartenzahlung getaetigt");
                alert.setContentText("Ihre Transaktion war erfolgreich, "+cardpaysum_field.getText()+"€ wurden ausgegeben.");

                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Kartenzahlung");
            alert.setHeaderText("Transaktionsfehler");
            alert.setContentText("Ihre Karte wurde noch nicht verifiziert, bitte führen Sie ihre Karte ein und probieren Sie es erneut");

            alert.showAndWait();
        }
    }

}

