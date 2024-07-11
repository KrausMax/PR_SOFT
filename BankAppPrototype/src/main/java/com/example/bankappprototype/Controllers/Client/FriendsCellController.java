package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Account;
import com.example.bankappprototype.Models.Friends;
import com.example.bankappprototype.Models.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for managing individual friend cells in the friends ListView.
 */
public class FriendsCellController implements Initializable {

    public Label client_lbl;
    public Button space_btn;
    public Button friend_btn;

    private final Friends friends;
    private Model model;

    /**
     * Constructor for FriendsCellController.
     *
     * @param friends The Friends object containing the data for the friend to be displayed.
     */
    public FriendsCellController(Friends friends) {
        this.friends = friends;
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = Model.getInstance();

        if (model.getClient().idProperty().getValue() == Integer.parseInt(friends.clientProperty().getValue())){
            client_lbl.textProperty().bind(new SimpleStringProperty(Model.getInstance().getClientEmailByID(Integer.parseInt(friends.friendProperty().getValue()))));
        }else {
            client_lbl.textProperty().bind(new SimpleStringProperty(Model.getInstance().getClientEmailByID(Integer.parseInt(friends.clientProperty().getValue()))));
        }

        friend_btn.setOnAction(ActionEvent -> showDeleteFriendDialogue());

        if (friends.sharedSpaceProperty().getValue() == 0){
            space_btn.setOnAction(ActionEvent -> showAddSharedSpaceDialog());
        }else {
            space_btn.setText("Space entfernen");
            space_btn.setOnAction(ActionEvent -> showRemoveSharedSpaceDialog());
        }
    }

    /**
     * Shows a dialog to confirm the removal of a shared space.
     */
    private void showRemoveSharedSpaceDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Shared Space entfernen");
        alert.setContentText("Wollen Sie den Shared Space wirklich entfernen? Sie werden den Zugriff zum Shared Space verlieren (außer Sie besitzen den Space)");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (model.deleteSharedSpaceByIDs(Integer.parseInt(friends.clientProperty().getValue()),Integer.parseInt(friends.friendProperty().getValue()))){
                model.setFriends();
                showInfoAlert("Information","Shared Space entfernen", "Der Shared Space wurde erfolgreich entfernt");
            }else {
                showInfoAlert("Information","Fehler", "Bei der Verarbeitung ihrer Anfrage ist ein Fehler aufgetaucht, bitte versuchen Sie es später noch einmal");
            }
        }
    }

    /**
     * Shows a dialog to confirm the deletion of a friend.
     */
    private void showDeleteFriendDialogue() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Freund löschen");
        alert.setContentText("Wollen Sie diese Person wirklich aus ihrer Freundesliste löschen? Sie werden ebenso Zugriff zum Shared Space verlieren (außer Sie besitzen den Space)");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if (model.deleteFriendsByIDs(Integer.parseInt(friends.clientProperty().getValue()),Integer.parseInt(friends.friendProperty().getValue()))){
                model.setFriends();
                showInfoAlert("Information","Freund entfernen", "Ihr Freund wurde erfolgreich entfernt");
            }else {
                showInfoAlert("Information","Fehler", "Bei der Verarbeitung ihrer Anfrage ist ein Fehler aufgetaucht, bitte versuchen Sie es später noch einmal");
            }
        }
    }

    /**
     * Shows a dialog to add a shared space to the friend.
     */
    private void showAddSharedSpaceDialog() {
        ChoiceDialog<String> cDialog = new ChoiceDialog<>();
        ObservableList<String> list = cDialog.getItems();
        for (Account account:model.getSpaces()){
            list.add(account.accountNumberProperty().getValue());
        }
        if (list.isEmpty()){
            showInfoAlert("Information","Sie haben keinen Space zu teilen", "Kaufen Sie sich einen Space um diesen mit Freunden teilen zu können");
            return;
        }
        cDialog.setTitle("Share Space");
        cDialog.setHeaderText("Geben Sie eine Space frei, um diesen gemeinsam nutzen zu können");
        cDialog.setContentText("IBAN: ");

        Optional<String> result = cDialog.showAndWait();
        if (result.isPresent()){
            for (Account account:model.getSpaces()){
                if (account.accountNumberProperty().getValue().equals(result.get())){
                    addSharedSpace(account.idProperty().getValue());
                }
            }
        }

    }

    /**
     * Adds a shared space to the friend.
     *
     * @param spaceId The ID of the space to be shared.
     */
    private void addSharedSpace(int spaceId) {
        if(Model.getInstance().addSharedSpace(Integer.parseInt(friends.clientProperty().getValue()),Integer.parseInt(friends.friendProperty().getValue()),spaceId)){
            Model.getInstance().setFriends();
            showInfoAlert("Information","Space geteilt", "Ihr Space wurde erfolgreich mit ihrem Freund geteilt");
        }else {
            showInfoAlert("Information","Fehler", "Bei der Verarbeitung ihrer Anfrage ist ein Fehler aufgetaucht, bitte versuchen Sie es später noch einmal");
        }
    }

    /**
     * Shows an information alert with the specified title, header, and content.
     *
     * @param title   The title of the alert.
     * @param header  The header text of the alert.
     * @param context The content text of the alert.
     */
    private void showInfoAlert(String title, String header, String context){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }
}
