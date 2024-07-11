package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Models.SavingsAccount;
import com.example.bankappprototype.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for a space cell displayed on the dashboard, allowing the user to view space information and utilize its functions.
 */
public class SpaceCellController implements Initializable {
    public Label account_number_lbl;
    public Label balance_lbl;
    public Label spaceName_lbl;
    public AnchorPane space_pane;
    public Button showCards_btn;
    public ImageView space_picture_imageview;
    public Button transfer_btn;

    private final SavingsAccount space;

    /**
     * Constructor for SpaceCellController.
     *
     * @param space The SavingsAccount associated with this cell.
     */
    public SpaceCellController(SavingsAccount space){
        this.space = space;
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account_number_lbl.setText(space.accountNumberProperty().getValue());
        balance_lbl.setText(String.valueOf(space.balanceProperty().getValue()));
        showCards_btn.setOnAction(Event -> showCards());
        Image image = new Image(space.getSpaceImage());
        space_picture_imageview.setImage(image);
        spaceName_lbl.setText(space.getSpaceName());
        transfer_btn.setOnAction(actionEvent -> transfer());
    }

    /**
     * Transfers money to or from the space.
     */
    private void transfer() {
        Model.getInstance().getClient().setSavingsAccount(space);
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.SPACE_TRANSFER);
    }

    /**
     * Shows the cards associated with the space.
     */
    private void showCards() {
        Model.getInstance().setCardIban(account_number_lbl.getText());
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CARDS);
    }
}
