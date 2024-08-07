package com.example.bankappprototype.Controllers.Client;

import com.example.bankappprototype.Models.CheckingAccount;
import com.example.bankappprototype.Models.Model;
import com.example.bankappprototype.Views.ClientMenuOptions;
import com.example.bankappprototype.Views.SpaceCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for the dashboard, where the client can get an overview of their accounts and access various account features.
 */
public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label income_lbl;
    public Label expense_lbl;
    public TextField payee_fld;
    public TextArea message_fld;
    public Button send_money_btn;
    public Label space_lbl;
    public TextField name_fld;
    public Label space_lbl1;
    public ListView space_listview;
    public Label mainAccNum_lbl;
    public Button showCards_btn;
    public Button transfer_btn;

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckingAccount mainAcc = Model.getInstance().getClient().checkingAccountProperty().getValue();
        checking_bal.setText("" + Model.getInstance().getDatabaseDriver().getAccountBalance(mainAcc.accountNumberProperty().get()));
        mainAccNum_lbl.setText(mainAcc.accountNumberProperty().get());

        // Greeting Message
        String firstName = Model.getInstance().getClient().firstNameProperty().get();
        user_name.setText("Hallo " + firstName);

        // Current Date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        login_date.setText(currentDate.format(formatter));

        initSpaceData();
        space_listview.setItems(Model.getInstance().getSpaces());
        space_listview.setCellFactory(e -> new SpaceCellFactory());

        showCards_btn.setOnAction(Event -> showCards());
        transfer_btn.setOnAction(Event -> transfer());
    }

    /**
     * Navigates to the transfer view.
     */
    private void transfer() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSFER);
    }

    /**
     * Navigates to the cards view.
     */
    private void showCards() {
        Model.getInstance().setCardIban(mainAccNum_lbl.getText());
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.CARDS);
    }

    /**
     * Initializes the space data for the client's checking account.
     */
    private void initSpaceData() {
        setCurrentSpaces(Model.getInstance().getClient().checkingAccountProperty().getValue().idProperty().getValue());
    }

    /**
     * Sets the current spaces for the given account ID.
     *
     * @param accountID The ID of the account for which to set the spaces.
     */
    private void setCurrentSpaces(int accountID) {
        Model.getInstance().setActiveAccount(accountID);
        Model.getInstance().setSpaces(accountID);
    }
}
