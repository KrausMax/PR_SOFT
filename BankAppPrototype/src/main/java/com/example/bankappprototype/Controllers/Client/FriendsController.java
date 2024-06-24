package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Account;
import com.example.bankappprototype.Models.Friends;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.FriendsCellFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {
    public ListView friends_listview;
    public ListView sharedSpace_listview;
    public Button addFriend_btn;
    public TextField addFriend_fld;
    public Button request_btn;
    public Button fasttrans_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        friends_listview.setItems(Model.getInstance().getFriends());
        friends_listview.setCellFactory(e -> new FriendsCellFactory());

        sharedSpace_listview.setItems(Model.getInstance().getShared_spaces());

        addFriend_btn.setOnAction(ActionEvent -> addNewFriend(addFriend_fld.getText()));

        fasttrans_btn.setOnAction(ActionEvent -> showFastTransactionDialog());

        request_btn.setOnAction(ActionEvent -> showRequestMoneyDialog());

    }

    private void addNewFriend(String text) {
        int newFriendID = Model.getInstance().getClientIDByEmail(text);
        if (newFriendID == 0 || newFriendID == 1){
            showInfoAlert("Information","Email nicht gefunden", "Leider konnten wir ihren Freund nicht finden, bitte überprüfen Sie die genutzte Email und probieren Sie es erneut.");

            return;
        }
        if (friendAlreadyExists(newFriendID)){
            showInfoAlert("Information","Freundschaft existiert bereits", "Mit der angegebenen Person sind Sie bereits befreundet");

            return;
        }
        if (Model.getInstance().addNewFriend(newFriendID)){
            Model.getInstance().setFriends();
            showInfoAlert("Information","Neuer Freund", "Sie haben erfolgreich einen neuen Freund hinzugefügt, " +
                    "über den add Space Knopf können Sie nun einen ihrer Spaces zu einem Shared Space transformieren, " +
                    "oder einen Space ihres Freundes nutzen.");
        }else {
            showInfoAlert("Information","Fehler", "Bei der Verarbeitung ihrer Anfrage ist ein Fehler aufgetaucht, bitte versuchen Sie es später noch einmal");
        }
    }

    private void showFastTransactionDialog() {

        Optional<Pair<String, String>> result = createCustomDialog("Schnell überweisung","Wählen Sie ihren Freund und den Überweisungsbetrag");

        if (result == null){
            return;
        }
        result.ifPresent(usernamePassword -> {

            if (true){ // <-- Überweisung fehlt hier
                showInfoAlert("Information","Neue Überweisung", "Ihre Überweisung an "+ usernamePassword.getKey()+" für "+ usernamePassword.getValue() +"€ war erfolgreich ");
            }
        });
    }

    private void showRequestMoneyDialog(){
        Optional<Pair<String, String>> result = createCustomDialog("Geld Anfragen","Wählen Sie ihren Freund und den Geldbetrag");

        if (result == null){
            return;
        }
        result.ifPresent(usernamePassword -> {

            if (true){ // <-- Überweisung fehlt hier
                showInfoAlert("Information","Neue Geld Anfrage", "Ihre Anfrage an "+ usernamePassword.getKey()+" für "+ usernamePassword.getValue() +"€ war erfolgreich ");
            }
        });
    }

    private Optional<Pair<String, String>> createCustomDialog(String title, String header){

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the friend and amount labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<String> friendBox = new ComboBox<>();

        ObservableList<String> list = friendBox.getItems();
        for (Friends friends:Model.getInstance().getFriends()){
            if (Integer.parseInt(friends.clientProperty().getValue()) == Model.getInstance().getClient().idProperty().getValue()){
                list.add(Model.getInstance().getClientEmailByID(Integer.parseInt(friends.friendProperty().getValue())));
            }else {
                list.add(Model.getInstance().getClientEmailByID(Integer.parseInt(friends.clientProperty().getValue())));
            }
        }

        TextField amount = new TextField();
        amount.setPromptText("€€€");

        grid.add(new Label("Freund:"), 0, 0);
        grid.add(friendBox, 1, 0);
        grid.add(new Label("Betrag:"), 0, 1);
        grid.add(amount, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a friend-amount-pair when the ok button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(friendBox.getValue(), amount.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (result.isPresent()){
            return result;
        }
        return null;
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

    private void showInfoAlert(String title,String header, String context){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }


    public void viewSharedSpace(MouseEvent mouseEvent) {
        Account shared_space = (Account) sharedSpace_listview.getSelectionModel().getSelectedItem();
        showInfoAlert("Shared Space", "Besitzer: "+Model.getInstance().getClientEmailByID(shared_space.ownerProperty().getValue())+" \n\n IBAN: "+shared_space+"\n Guthaben: "+shared_space.balanceProperty().getValue()+"€","Members:"+Model.getInstance().getSharedSpaceMembersBySpaceID(shared_space.idProperty().getValue()));
    }
}
