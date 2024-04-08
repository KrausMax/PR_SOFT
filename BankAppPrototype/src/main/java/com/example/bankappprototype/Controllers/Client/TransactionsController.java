package com.example.bankappprototype.Controllers.Client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView transactions_listview;
    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;
    public FontAwesomeIconView in_icon1;
    public FontAwesomeIconView out_icon1;
    public Label trans_date_lbl1;
    public Label sender_lbl1;
    public Label receiver_lbl1;
    public Label amount_lbl1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
