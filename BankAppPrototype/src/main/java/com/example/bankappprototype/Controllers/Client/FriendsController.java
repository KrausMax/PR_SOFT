package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Friends;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.FriendsCellFactory;
import com.example.bankappprototype.Views.TransactionCellFactory;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {
    public ListView friends_listview;
    public Button addFriend_btn;
    public TextField addFriend_fld;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        friends_listview.setItems(Model.getInstance().getFriends());
        friends_listview.setCellFactory(e -> new FriendsCellFactory());

        addFriend_btn.setOnAction(ActionEvent -> addNewFriend(addFriend_fld.getText()));

    }

    private void addNewFriend(String text) {
        int newFriendID = Model.getInstance().getClientIDByEmail(text);
        if (newFriendID == 0 || newFriendID == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Email nicht gefunden");
            alert.setContentText("Leider konnten wir ihren Freund nicht finden, bitte überprüfen Sie die genutzte Email und probieren Sie es erneut.");

            alert.showAndWait();
            return;
        }
        if (friendAlreadyExists(newFriendID)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Freundschaft existiert bereits");
            alert.setContentText("Mit der angegebenen Person sind Sie bereits befreundet");

            alert.showAndWait();
            return;
        }
        if (Model.getInstance().addNewFriend(newFriendID)){
            Model.getInstance().setFriends();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Neuer Freund");
            alert.setContentText("Sie haben erfolgreich einen neuen Freund hinzugefügt, " +
                    "über den add Space Knopf können Sie nun einen ihrer Spaces zu einem Shared Space transformieren, " +
                    "oder einen Space ihres Freundes nutzen.");

            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Fehler");
            alert.setContentText("Bei der Verarbeitung ihrer Anfrage ist ein Fehler aufgetaucht, bitte versuchen Sie es später noch einmal");

            alert.showAndWait();
        }
    }

    private boolean friendAlreadyExists(int newFriendID) {
        int currentClient = Model.getInstance().getClient().idProperty().getValue();
        for (Friends iFriends:Model.getInstance().getFriends()){
            if (currentClient == Integer.parseInt(iFriends.clientProperty().getValue()) || currentClient == Integer.parseInt(iFriends.friendProperty().getValue())){
                if (newFriendID == Integer.parseInt(iFriends.clientProperty().getValue()) || newFriendID == Integer.parseInt(iFriends.friendProperty().getValue())){
                    return true;
                }
            }
        }
        return false;
    }

    private void initData() {
        if(Model.getInstance().getFriends().isEmpty()) {
            Model.getInstance().setFriends();
        }
    }
}
