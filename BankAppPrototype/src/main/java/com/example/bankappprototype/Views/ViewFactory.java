package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Admin.AdminController;
import com.example.bankappprototype.Controllers.Client.CardsController;
import com.example.bankappprototype.Controllers.Client.ClientController;
import com.example.bankappprototype.Models.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Factory class to manage and load different views in the application.
 */
public class ViewFactory {
    private AccountType loginAccountType;
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane accountsView;
    private AnchorPane transferView;
    private AnchorPane spaceTransferView;
    private AnchorPane profileView;
    private AnchorPane createSpaceView;
    private AnchorPane flowChartView;
    private AnchorPane createClientView;
    private AnchorPane clientsView;
    private AnchorPane friendsView;
    private AnchorPane reportView;
    private AnchorPane bankomatView;
    private AnchorPane cardsView;
    private AnchorPane createCardView;
    private AnchorPane editCardView;

    /**
     * Constructor for ViewFactory.
     */
    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    /**
     * Gets the login account type.
     *
     * @return the login account type
     */
    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    /**
     * Sets the login account type.
     *
     * @param loginAccountType the login account type to set
     */
    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /**
     * Gets the client selected menu item property.
     *
     * @return the client selected menu item property
     */
    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    /**
     * Gets the dashboard view.
     *
     * @return the dashboard view
     */
    public AnchorPane getDashboardView() {
        try {
            dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dashboardView;
    }

    /**
     * Gets the transaction view.
     *
     * @return the transaction view
     */
    public AnchorPane getTransactionView() {
        try {
            transactionView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transactions.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionView;
    }

    /**
     * Gets the create card view.
     *
     * @return the create card view
     */
    public AnchorPane getCreateCardView() {
        try {
            createCardView = new FXMLLoader(getClass().getResource("/Fxml/Client/CreateCard.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createCardView;
    }

    /**
     * Gets the edit card view.
     *
     * @return the edit card view
     */
    public AnchorPane getEditCardView() {
        try {
            editCardView = new FXMLLoader(getClass().getResource("/Fxml/Client/EditCard.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editCardView;
    }

    /**
     * Gets the accounts view.
     *
     * @return the accounts view
     */
    public AnchorPane getAccountsView() {
        if (accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    /**
     * Gets the profile view.
     *
     * @return the profile view
     */
    public AnchorPane getProfileView() {
        if (profileView == null) {
            try {
                profileView = new FXMLLoader(getClass().getResource("/Fxml/Client/Profile.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }

    /**
     * Gets the friends view.
     *
     * @return the friends view
     */
    public AnchorPane getFriendsView() {
        if (friendsView == null) {
            try {
                friendsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Friends.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return friendsView;
    }

    /**
     * Gets the report view.
     *
     * @return the report view
     */
    public AnchorPane getReportView() {
        if (reportView == null) {
            try {
                reportView = new FXMLLoader(getClass().getResource("/Fxml/Client/Report.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reportView;
    }

    /**
     * Gets the create space view.
     *
     * @return the create space view
     */
    public AnchorPane getCreateSpaceView() {
        try {
            createSpaceView = new FXMLLoader(getClass().getResource("/Fxml/Client/CreateSpace.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createSpaceView;
    }

    /**
     * Gets the bankomat view.
     *
     * @return the bankomat view
     */
    public AnchorPane getBankomatView() {
        if (bankomatView == null) {
            try {
                bankomatView = new FXMLLoader(getClass().getResource("/Fxml/Client/Bankomat.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bankomatView;
    }

    /**
     * Gets the flow chart view.
     *
     * @return the flow chart view
     */
    public AnchorPane getFlowChartView() {
        if (flowChartView == null) {
            try {
                flowChartView = new FXMLLoader(getClass().getResource("/Fxml/Client/FlowChart.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flowChartView;
    }

    /**
     * Gets the cards view.
     *
     * @return the cards view
     */
    public AnchorPane getCardsView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Cards.fxml"));
        CardsController controller = new CardsController(Model.getInstance().getCardIban());
        loader.setController(controller);
        try {
            cardsView = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsView;
    }

    /**
     * Gets the transfer view.
     *
     * @return the transfer view
     */
    public AnchorPane getTransferView() {
        try {
            transferView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transfer.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transferView;
    }

    /**
     * Gets the space transfer view.
     *
     * @return the space transfer view
     */
    public AnchorPane getSpaceTransferView() {
        try {
            spaceTransferView = new FXMLLoader(getClass().getResource("/Fxml/Client/SpaceTransfer.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spaceTransferView;
    }

    /**
     * Displays the client window.
     */
    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);

        createStage(loader);
    }

    /**
     * Gets the admin selected menu item property.
     *
     * @return the admin selected menu item property
     */
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    /**
     * Gets the create client view.
     *
     * @return the create client view
     */
    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateClient.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    /**
     * Gets the clients view.
     *
     * @return the clients view
     */
    public AnchorPane getClientsView() {
        if (clientsView == null) {
            try {
                clientsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Clients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientsView;
    }

    /**
     * Displays the admin window.
     */
    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);

        createStage(loader);
    }

    /**
     * Displays the login window.
     */
    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    // Creates and displays a new stage with the given loader
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/pepe.png"))));
        stage.setResizable(false);
        stage.setTitle("Elysia Bank");
        stage.show();
    }

    /**
     * Closes the given stage.
     *
     * @param stage the stage to close
     */
    public void closeStage(Stage stage) {
        stage.close();
    }
}
