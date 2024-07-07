package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Admin.AdminController;
import com.example.bankappprototype.Controllers.Client.CardsController;
import com.example.bankappprototype.Controllers.Client.ClientController;
import com.example.bankappprototype.Models.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private AccountType loginAccountType;
    //Client Views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane accountsView;
    private AnchorPane transferView;
    private AnchorPane spaceTransferView;
    private AnchorPane profileView;
    private AnchorPane createSpaceView;
    private AnchorPane flowChartView;

    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;
    private AnchorPane depositView;
    private AnchorPane friendsView;
    private AnchorPane reportView;
    private AnchorPane bankomatView;
    private AnchorPane cardsView;
    private AnchorPane createCardView;
    private AnchorPane editCardView;


    public ViewFactory(){
        this.loginAccountType = AccountType.CLIENT;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
    * Client Views Section
    * */

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {

        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView(){
        try {
            dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
        } catch (Exception e) {
                e.printStackTrace();
        }
        return dashboardView;
    }

    public AnchorPane getTransactionView() {
        try {
            transactionView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transactions.fxml")).load();
        } catch (Exception e){
            e.printStackTrace();
        }
        return transactionView;
    }

    public AnchorPane getCreateCardView() {
        try {
            createCardView = new FXMLLoader(getClass().getResource("/Fxml/Client/CreateCard.fxml")).load();
        } catch (Exception e){
            e.printStackTrace();
        }
        return createCardView;
    }

    public AnchorPane getEditCardView() {
        try {
            editCardView = new FXMLLoader(getClass().getResource("/Fxml/Client/EditCard.fxml")).load();
        } catch (Exception e){
            e.printStackTrace();
        }
        return editCardView;
    }

    public AnchorPane getAccountsView() {
        if(accountsView == null){
            try {
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    public AnchorPane getProfileView() {
        if(profileView == null){
            try {
                profileView = new FXMLLoader(getClass().getResource("/Fxml/Client/Profile.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return profileView;
    }

    public AnchorPane getFriendsView() {
        if(friendsView == null){
            try {
                friendsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Friends.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return friendsView;
    }

    public AnchorPane getReportView() {
        if(reportView == null){
            try {
                reportView = new FXMLLoader(getClass().getResource("/Fxml/Client/Report.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return reportView;
    }

    public AnchorPane getCreateSpaceView() {
        try {
            createSpaceView = new FXMLLoader(getClass().getResource("/Fxml/Client/CreateSpace.fxml")).load();
        } catch (Exception e) {
                e.printStackTrace();
            }
        return createSpaceView;
    }

    public AnchorPane getBankomatView() {
        if(bankomatView == null){
            try {
                bankomatView = new FXMLLoader(getClass().getResource("/Fxml/Client/Bankomat.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return bankomatView;
    }

    public AnchorPane getFlowChartView() {
        if(flowChartView == null){
            try {
                flowChartView = new FXMLLoader(getClass().getResource("/Fxml/Client/FlowChart.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return flowChartView;
    }

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

    public AnchorPane getTransferView() {
        try {
            transferView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transfer.fxml")).load();
        } catch (Exception e){
            e.printStackTrace();
        }
        return transferView;
    }

    public AnchorPane getSpaceTransferView() {
        try {
            spaceTransferView = new FXMLLoader(getClass().getResource("/Fxml/Client/SpaceTransfer.fxml")).load();
        } catch (Exception e){
            e.printStackTrace();
        }
        return spaceTransferView;
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);

        createStage(loader);
    }

    /*
    * Admin Views Section
    * */
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }
    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateClient.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public AnchorPane getClientsView() {
        if(clientsView == null) {
            try {
                clientsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Clients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientsView;
    }

    public AnchorPane getDepositView () {
        if(depositView == null) {
            try {
                depositView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Deposit.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return depositView;
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);

        createStage(loader);
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/pepe.png"))));
        stage.setResizable(false);
        stage.setTitle("Elysia Bank");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
