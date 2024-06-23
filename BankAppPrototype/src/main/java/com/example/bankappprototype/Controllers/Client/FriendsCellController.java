package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Account;
import com.example.bankappprototype.Models.Friends;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FriendsCellController implements Initializable {

    public FontAwesomeIconView in_icon;
    public Label client_lbl;
    public Label space_name_lbl;
    public Label space_lbl;
    public Label amount_lbl;
    public Label amount_lbl1;
    public Button space_btn;

    private final Friends friends;

    Model model;

    public FriendsCellController(Friends friends) {
        this.friends = friends;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = Model.getInstance();

        if (model.getClient().idProperty().getValue() == Integer.parseInt(friends.clientProperty().getValue())){
            client_lbl.textProperty().bind(new SimpleStringProperty(Model.getInstance().getClientEmailByID(Integer.parseInt(friends.friendProperty().getValue()))));
        }else {
            client_lbl.textProperty().bind(new SimpleStringProperty(Model.getInstance().getClientEmailByID(Integer.parseInt(friends.clientProperty().getValue()))));
        }

        if (friends.sharedSpaceProperty().getValue() == 0){
            space_lbl.setVisible(false);
            space_name_lbl.setVisible(false);
            amount_lbl.setVisible(false);
            amount_lbl1.setVisible(false);
            space_btn.setOnAction(ActionEvent -> showAddSharedSpaceDialog());
        }else {
            Account space = model.getSpace(friends.sharedSpaceProperty().getValue());
            space_lbl.setText("**** **** **** **** ****");
            space_name_lbl.setText("Shared Space");
            amount_lbl.setText(space.balanceProperty().getValue().toString());
            space_btn.setVisible(false);
            space_lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    space_lbl.setText(space.accountNumberProperty().getValue());
                }
            });
        }
    }

    private void showAddSharedSpaceDialog() {
        ChoiceDialog<String> cDialog = new ChoiceDialog<>();
        ObservableList<String> list = cDialog.getItems();
        for (Account account:Model.getInstance().getSpaces()){
            list.add(account.accountNumberProperty().getValue());
        }
        cDialog.setTitle("Share Space");
        cDialog.setHeaderText("Geben Sie eine Space frei, um diesen gemeinsam nutzen zu können");
        cDialog.setContentText("IBAN: ");

        Optional<String> result = cDialog.showAndWait();
        if (result.isPresent()){
            for (Account account:Model.getInstance().getSpaces()){
                if (account.accountNumberProperty().getValue().equals(result.get())){
                    addSharedSpace(account.idProperty().getValue());
                }
            }
        }

    }

    private void addSharedSpace(int spaceId) {
        if(Model.getInstance().addSharedSpace(Integer.parseInt(friends.clientProperty().getValue()),Integer.parseInt(friends.friendProperty().getValue()),spaceId)){
            Model.getInstance().setFriends();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Space geteilt");
            alert.setContentText("Ihr Space wurde erfolgreich mit ihrem Freund geteilt");

            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Fehler");
            alert.setContentText("Bei der Verarbeitung ihrer Anfrage ist ein Fehler aufgetaucht, bitte versuchen Sie es später noch einmal");

            alert.showAndWait();
        }
    }
}
