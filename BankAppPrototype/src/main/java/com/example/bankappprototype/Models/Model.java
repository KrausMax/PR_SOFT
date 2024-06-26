package com.example.bankappprototype.Models;

import com.example.bankappprototype.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Model {

    //Singleton
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    // Client Data Section
    private final Client client;
    private boolean clientLoginSuccessFlag;
    private final ObservableList<Transaction> transactions;
    private final ObservableList<Friends> friends;
    private final ObservableList<Account> spaces;
    private final ObservableList<Card> cards;
    private final ObservableList<Account> shared_spaces;
    private int activeAccount;
    private String cardIban;
    private String cardNum;

    // Admin Data Section
    private boolean adminLoginSuccessFlag;
    private final ObservableList<Client> clients;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("","","","",1,null,null, null);
        this.transactions = FXCollections.observableArrayList();
        this.friends = FXCollections.observableArrayList();
        this.spaces = FXCollections.observableArrayList();
        this.cards = FXCollections.observableArrayList();
        this.shared_spaces = FXCollections.observableArrayList();
        // Admin Data Section
        this.adminLoginSuccessFlag = false;
        this.clients = FXCollections.observableArrayList();

    }

    public static synchronized Model getInstance() {
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    /*
    * Client Method Section
    * */

    public boolean getClientLoginSuccessFlag() {
        return this.clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    public Client getClient() {
        return client;
    }

    public String getClientEmailByAccountID(int id){
        return databaseDriver.getClientEmailByAccountID(id);
    }

    public String getClientEmailByID(int id){
        return databaseDriver.getClientEmailByID(id);
    }

    public String verifyCard(String cardNumber, String sequenceNumber, String secretNumber){
        return databaseDriver.verifyCard(cardNumber,sequenceNumber,secretNumber);
    }

    public void evaluateClientCred(String pAddress, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getClientData(pAddress, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.pAddressProperty().set(resultSet.getString("email"));
                this.client.pwordProperty().set(resultSet.getString("Password"));
                this.client.idProperty().set(resultSet.getInt("ID"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateProperty().set(date);
                checkingAccount = getCheckingAccount(resultSet.getInt("ID"));
                savingsAccount = getSavingsAccount(resultSet.getInt("ID"));
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.savingsAccountProperty().set(savingsAccount);
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(int account_id) {
        transactions.clear();

        ResultSet resultSet = databaseDriver.getAllTransactionsOfClient(account_id);

        try {
            while(resultSet.next()) {
                String sender = String.valueOf(resultSet.getInt("Sender"));
                String receiver = String.valueOf(resultSet.getInt("Receiver"));
                double amount = resultSet.getDouble("Amount");
                String message = resultSet.getString("Message");
                String type = resultSet.getString("transaction_type");
                String [] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                transactions.add(0,new Transaction(sender, receiver, amount, date, message, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFriends() {
        friends.clear();

        ResultSet resultSet = databaseDriver.getAllFriendsOfClient(client.idProperty().getValue());

        try {
            while(resultSet.next()) {
                String client = String.valueOf(resultSet.getInt("Client"));
                String friend = String.valueOf(resultSet.getInt("FriendClient"));
                int sharedSpace = resultSet.getInt("SharedSpace");
                friends.add(0,new Friends(client, friend, sharedSpace));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setShared_spaces();
    }

    public ObservableList<Friends> getFriends() {
        return friends;
    }

    public ObservableList<Account> getShared_spaces(){
        if (shared_spaces.isEmpty())
            setShared_spaces();

        return shared_spaces;
    }
    public void setShared_spaces(){
        shared_spaces.clear();
        ResultSet resultSet;
        for (Friends tFriends:friends){
            if (tFriends.sharedSpaceProperty().getValue()>1){
                resultSet = databaseDriver.getSpaceByID(tFriends.sharedSpaceProperty().getValue());
                try {
                    Account tempAcc = new Account(resultSet.getInt(2),resultSet.getString(3),resultSet.getDouble(5),resultSet.getInt(1));
                    if (shared_spaces.stream().anyMatch(space -> space.idProperty().getValue() == tempAcc.idProperty().getValue())){
                        continue;
                    }
                    shared_spaces.add(tempAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public ObservableList<Card> getCards() { return cards; }
    public void setCards(String iban) {
        cards.clear();

        ResultSet resultSet = databaseDriver.getCards(iban);

        try {
            while(resultSet.next()) {
                int account =resultSet.getInt("Account");
                String cardNumber = resultSet.getString("CardNumber");
                int sequenceNumber = resultSet.getInt("SequenceNumber");
                int pin = resultSet.getInt("SecretNumber");
                cards.add(0,new Card(account, cardNumber, sequenceNumber, pin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Account> getSpaces() {
        return spaces;
    }

    public void setSpaces(int account_id) {
        spaces.clear();

        ResultSet resultSet = databaseDriver.getAllSpaces(account_id);

        try {
            while(resultSet.next()) {
                int owner = resultSet.getInt("Owner");
                String accountNumber = resultSet.getString("AccountNumber");
                double balance = resultSet.getDouble("Balance");
                int id = resultSet.getInt("ID");
                spaces.add(0,new Account(owner, accountNumber, balance, id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Admin Method Section
    * */

    public boolean getAdminLoginSuccessFlag() {
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCred(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.adminLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients() {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getAllClientsData();
        try {
            while(resultSet.next()) {
                String fName = resultSet.getString("FirstName");
                String lName = resultSet.getString("LastName");
                String pAddress = resultSet.getString("email");
                String pword = resultSet.getString("Password");
                int id = resultSet.getInt("ID");
                String [] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                checkingAccount = getCheckingAccount(resultSet.getInt("ID"));
                savingsAccount = getSavingsAccount(resultSet.getInt("ID"));
                clients.add(new Client(fName, lName, pAddress, pword, id, checkingAccount, savingsAccount, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    * Utility Methods Sections
    * */

    public CheckingAccount getCheckingAccount(int owner) {
        CheckingAccount account = null;
        ResultSet resultSet = databaseDriver.getCheckingAccountData(owner);
        try {
            String num = resultSet.getString("AccountNumber");
            int tLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            int id = resultSet.getInt("ID");
            account = new CheckingAccount(owner, num, balance, tLimit, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public SavingsAccount getSavingsAccount(int owner) {
        SavingsAccount account = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountData(owner);
        try {
            String num = resultSet.getString("AccountNumber");
            double wLimit = resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            int id = resultSet.getInt("ID");
            account = new SavingsAccount(owner, num, balance, wLimit, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public int getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(int activeAccount) {
        this.activeAccount = activeAccount;
    }

    public boolean addToCard(String accountID, String amount) {
        return databaseDriver.addToCardPayment(accountID,amount);
    }

    public boolean subtractFromCard(String accountID, String amount) {
        return databaseDriver.subtractFromCardPayment(accountID,amount);
    }

    public boolean payWithCard(String accountID, String amount, String message) {
        return databaseDriver.payWithCard(accountID,amount,message);
    }

    public String getCardIban() {
        return cardIban;
    }
    public void setCardIban(String iban) {
        this.cardIban = iban;
    }

    public boolean addNewFriend(int newFriendID) {
        return databaseDriver.addNewFriend(client.idProperty().getValue(),newFriendID);
    }

    public int getClientIDByEmail(String text) {
        return databaseDriver.getClientIDByEMail(text);
    }

    public boolean addSharedSpace(int client, int friend, int spaceId) {
        return databaseDriver.addSharedSpace(client,friend,spaceId);
    }

    public Account getSpace(int spaceId) {
        SavingsAccount account = null;
        ResultSet resultSet = databaseDriver.getAccountById(spaceId);
        try {
            String num = resultSet.getString("AccountNumber");
            double wLimit = resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            int owner = resultSet.getInt("Owner");
            account = new SavingsAccount(owner, num, balance, wLimit, spaceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public Set<String> getSharedSpaceMembersBySpaceID(int id) {

        Set<String> friendEmails = new TreeSet<>();

        ResultSet resultSet = databaseDriver.getSharedSpaceMembersBySharedSpaceID(id);

        try {
            while(resultSet.next()) {
                friendEmails.add(getClientEmailByID(resultSet.getInt("Client")));
                friendEmails.add(getClientEmailByID(resultSet.getInt("FriendClient")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendEmails;
    }

    public boolean deleteFriendsByIDs(int client, int friend) {
        return databaseDriver.deleteFriendsByIDs(client,friend);
    }

    public boolean deleteSharedSpaceByIDs(int client, int friend) {
        return databaseDriver.deleteSharedSpaceByIDs(client,friend);
    }
	
    public String getCardNum() { return cardNum; }
    public void setCardNum(String cardNum) {this.cardNum = cardNum; }
}

