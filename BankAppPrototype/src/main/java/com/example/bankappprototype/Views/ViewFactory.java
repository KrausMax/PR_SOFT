package com.example.bankappprototype.Views;

import com.example.bankappprototype.Controllers.Admin.AdminController;
import com.example.bankappprototype.Controllers.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
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

    private AnchorPane profileView;
    private AnchorPane createSpaceView;

    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane createClientView;
    private AnchorPane clientsView;
    private AnchorPane depositView;
    private AnchorPane friendsView;
    private AnchorPane reportView;
    private AnchorPane bankomatView;


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
        if(dashboardView == null){
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getTransactionView() {
        if(transactionView == null){
            try {
                transactionView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transactions.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return transactionView;
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
        if(createSpaceView == null) {
            try {
                createSpaceView = new FXMLLoader(getClass().getResource("/Fxml/Client/CreateSpace.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        stage.setTitle("Test Bank");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

}
