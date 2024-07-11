package com.example.bankappprototype.Models;

import com.example.bankappprototype.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

/**
 * Model class representing the application's data and logic.
 */
public class Model {

    // Singleton instance
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    // Client Data Section
    private final Client client;
    private boolean clientLoginSuccessFlag;
    private final ObservableList<Transaction> transactions;
    private final ObservableList<Friends> friends;
    private final ObservableList<SavingsAccount> spaces;
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
        this.client = new Client("", "", "", "", 1, null, null, null, "");
        this.transactions = FXCollections.observableArrayList();
        this.friends = FXCollections.observableArrayList();
        this.spaces = FXCollections.observableArrayList();
        this.cards = FXCollections.observableArrayList();
        this.shared_spaces = FXCollections.observableArrayList();
        // Admin Data Section
        this.adminLoginSuccessFlag = false;
        this.clients = FXCollections.observableArrayList();
    }

    /**
     * Gets the singleton instance of the Model.
     *
     * @return the singleton instance of the Model
     */
    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /**
     * Gets the view factory.
     *
     * @return the view factory
     */
    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    /**
     * Gets the database driver.
     *
     * @return the database driver
     */
    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    /*
     * Client Method Section
     */

    /**
     * Gets the client login success flag.
     *
     * @return the client login success flag
     */
    public boolean getClientLoginSuccessFlag() {
        return this.clientLoginSuccessFlag;
    }

    /**
     * Sets the client login success flag.
     *
     * @param flag the new client login success flag
     */
    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    /**
     * Gets the client.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets the client's email by account ID.
     *
     * @param id the account ID
     * @return the client's email
     */
    public String getClientEmailByAccountID(int id) {
        return databaseDriver.getClientEmailByAccountID(id);
    }

    /**
     * Gets the client's email by client ID.
     *
     * @param id the client ID
     * @return the client's email
     */
    public String getClientEmailByID(int id) {
        return databaseDriver.getClientEmailByID(id);
    }

    /**
     * Verifies a card based on the provided card number, sequence number, and secret number.
     *
     * @param cardNumber     the card number
     * @param sequenceNumber the sequence number
     * @param secretNumber   the secret number
     * @return the account number if the card is valid, null otherwise
     */
    public String verifyCard(String cardNumber, String sequenceNumber, String secretNumber) {
        return databaseDriver.verifyCard(cardNumber, sequenceNumber, secretNumber);
    }

    /**
     * Evaluates the client's credentials for login.
     *
     * @param pAddress the email address
     * @param password the password
     */
    public void evaluateClientCred(String pAddress, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getClientData(pAddress, password);
        try {
            if (resultSet.isBeforeFirst()) {
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
                this.client.imageProperty().set(resultSet.getString("image"));
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the transactions.
     *
     * @return the transactions
     */
    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the transactions for the specified account ID.
     *
     * @param account_id the account ID
     */
    public void setTransactions(int account_id) {
        transactions.clear();

        ResultSet resultSet = databaseDriver.getAllTransactionsOfClient(account_id);

        try {
            while (resultSet.next()) {
                String sender = String.valueOf(resultSet.getInt("Sender"));
                String receiver = String.valueOf(resultSet.getInt("Receiver"));
                double amount = resultSet.getDouble("Amount");
                String message = resultSet.getString("Message");
                String type = resultSet.getString("transaction_type");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                transactions.add(0, new Transaction(sender, receiver, amount, date, message, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the friends for the client.
     */
    public void setFriends() {
        friends.clear();

        ResultSet resultSet = databaseDriver.getAllFriendsOfClient(client.idProperty().getValue());

        try {
            while (resultSet.next()) {
                String client = String.valueOf(resultSet.getInt("Client"));
                String friend = String.valueOf(resultSet.getInt("FriendClient"));
                int sharedSpace = resultSet.getInt("SharedSpace");
                friends.add(0, new Friends(client, friend, sharedSpace));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setShared_spaces();
    }

    /**
     * Gets the friends.
     *
     * @return the friends
     */
    public ObservableList<Friends> getFriends() {
        return friends;
    }

    /**
     * Gets the shared spaces.
     *
     * @return the shared spaces
     */
    public ObservableList<Account> getShared_spaces() {
        if (shared_spaces.isEmpty())
            setShared_spaces();

        return shared_spaces;
    }

    /**
     * Sets the shared spaces.
     */
    public void setShared_spaces() {
        shared_spaces.clear();
        ResultSet resultSet;
        for (Friends tFriends : friends) {
            if (tFriends.sharedSpaceProperty().getValue() > 1) {
                resultSet = databaseDriver.getSpaceByID(tFriends.sharedSpaceProperty().getValue());
                try {
                    Account tempAcc = new Account(resultSet.getInt(2), resultSet.getString(3), resultSet.getDouble(5), resultSet.getInt(1));
                    if (shared_spaces.stream().anyMatch(space -> space.idProperty().getValue() == tempAcc.idProperty().getValue())) {
                        continue;
                    }
                    shared_spaces.add(tempAcc);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Gets the cards.
     *
     * @return the cards
     */
    public ObservableList<Card> getCards() {
        return cards;
    }

    /**
     * Sets the cards for the specified IBAN.
     *
     * @param iban the IBAN
     */
    public void setCards(String iban) {
        cards.clear();

        ResultSet resultSet = databaseDriver.getCards(iban);

        try {
            while (resultSet.next()) {
                int account = resultSet.getInt("Account");
                String cardNumber = resultSet.getString("CardNumber");
                int sequenceNumber = resultSet.getInt("SequenceNumber");
                int pin = resultSet.getInt("SecretNumber");
                cards.add(0, new Card(account, cardNumber, sequenceNumber, pin));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the spaces.
     *
     * @return the spaces
     */
    public ObservableList<SavingsAccount> getSpaces() {
        return spaces;
    }

    /**
     * Sets the spaces for the specified account ID.
     *
     * @param account_id the account ID
     */
    public void setSpaces(int account_id) {
        spaces.clear();

        ResultSet resultSet = databaseDriver.getAllSpaces(account_id);

        try {
            while (resultSet.next()) {
                int owner = resultSet.getInt("Owner");
                String accountNumber = resultSet.getString("AccountNumber");
                double balance = resultSet.getDouble("Balance");
                int id = resultSet.getInt("ID");
                double limit = resultSet.getDouble("TransactionLimit");
                String spaceName = resultSet.getString("space_name");
                String spaceImage = resultSet.getString("space_image");
                spaces.add(0, new SavingsAccount(owner, accountNumber, balance, limit, id, spaceName, spaceImage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Admin Method Section
     */

    /**
     * Gets the admin login success flag.
     *
     * @return the admin login success flag
     */
    public boolean getAdminLoginSuccessFlag() {
        return this.adminLoginSuccessFlag;
    }

    /**
     * Sets the admin login success flag.
     *
     * @param adminLoginSuccessFlag the new admin login success flag
     */
    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    /**
     * Evaluates the admin's credentials for login.
     *
     * @param username the username
     * @param password the password
     */
    public void evaluateAdminCred(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try {
            if (resultSet.isBeforeFirst()) {
                this.adminLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the clients.
     *
     * @return the clients
     */
    public ObservableList<Client> getClients() {
        return clients;
    }

    /**
     * Sets the clients.
     */
    public void setClients() {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getAllClientsData();
        try {
            while (resultSet.next()) {
                String fName = resultSet.getString("FirstName");
                String lName = resultSet.getString("LastName");
                String pAddress = resultSet.getString("email");
                String pword = resultSet.getString("Password");
                int id = resultSet.getInt("ID");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                checkingAccount = getCheckingAccount(resultSet.getInt("ID"));
                savingsAccount = getSavingsAccount(resultSet.getInt("ID"));
                String image = resultSet.getString("image");
                clients.add(new Client(fName, lName, pAddress, pword, id, checkingAccount, savingsAccount, date, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Utility Methods Sections
     */

    /**
     * Gets the checking account for the specified owner.
     *
     * @param owner the owner ID
     * @return the checking account
     */
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

    /**
     * Gets the savings account for the specified owner.
     *
     * @param owner the owner ID
     * @return the savings account
     */
    public SavingsAccount getSavingsAccount(int owner) {
        SavingsAccount account = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountData(owner);
        try {
            String num = resultSet.getString("AccountNumber");
            double wLimit = resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            int id = resultSet.getInt("ID");
            String spaceName = resultSet.getString("space_name");
            String spaceImage = resultSet.getString("space_image");
            account = new SavingsAccount(owner, num, balance, wLimit, id, spaceName, spaceImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * Gets the active account.
     *
     * @return the active account
     */
    public int getActiveAccount() {
        return activeAccount;
    }

    /**
     * Sets the active account.
     *
     * @param activeAccount the new active account
     */
    public void setActiveAccount(int activeAccount) {
        this.activeAccount = activeAccount;
    }

    /**
     * Gets the card IBAN.
     *
     * @return the card IBAN
     */
    public String getCardIban() {
        return cardIban;
    }

    /**
     * Sets the card IBAN.
     *
     * @param iban the new card IBAN
     */
    public void setCardIban(String iban) {
        this.cardIban = iban;
    }

    /**
     * Adds a new friend.
     *
     * @param newFriendID the new friend's ID
     * @return true if the friend was added successfully, false otherwise
     */
    public boolean addNewFriend(int newFriendID) {
        return databaseDriver.addNewFriend(client.idProperty().getValue(), newFriendID);
    }

    /**
     * Gets the client ID by email.
     *
     * @param text the email
     * @return the client ID
     */
    public int getClientIDByEmail(String text) {
        return databaseDriver.getClientIDByEMail(text);
    }

    /**
     * Adds a shared space.
     *
     * @param client  the client ID
     * @param friend  the friend ID
     * @param spaceId the space ID
     * @return true if the shared space was added successfully, false otherwise
     */
    public boolean addSharedSpace(int client, int friend, int spaceId) {
        return databaseDriver.addSharedSpace(client, friend, spaceId);
    }

    /**
     * Gets the space (account) details based on the space ID.
     *
     * @param spaceId the space ID
     * @return the space account
     */
    public Account getSpace(int spaceId) {
        SavingsAccount account = null;
        ResultSet resultSet = databaseDriver.getAccountById(spaceId);
        try {
            String num = resultSet.getString("AccountNumber");
            double wLimit = resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            int owner = resultSet.getInt("Owner");
            String spaceName = resultSet.getString("space_name");
            String spaceImage = resultSet.getString("space_image");
            account = new SavingsAccount(owner, num, balance, wLimit, spaceId, spaceName, spaceImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * Gets the shared space members by space ID.
     *
     * @param id the space ID
     * @return the set of shared space members' emails
     */
    public Set<String> getSharedSpaceMembersBySpaceID(int id) {
        Set<String> friendEmails = new TreeSet<>();
        ResultSet resultSet = databaseDriver.getSharedSpaceMembersBySharedSpaceID(id);

        try {
            while (resultSet.next()) {
                friendEmails.add(getClientEmailByID(resultSet.getInt("Client")));
                friendEmails.add(getClientEmailByID(resultSet.getInt("FriendClient")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendEmails;
    }

    /**
     * Deletes a friend relationship by client and friend IDs.
     *
     * @param client the client ID
     * @param friend the friend ID
     * @return true if the friend was deleted successfully, false otherwise
     */
    public boolean deleteFriendsByIDs(int client, int friend) {
        return databaseDriver.deleteFriendsByIDs(client, friend);
    }

    /**
     * Deletes a shared space by client and friend IDs.
     *
     * @param client the client ID
     * @param friend the friend ID
     * @return true if the shared space was deleted successfully, false otherwise
     */
    public boolean deleteSharedSpaceByIDs(int client, int friend) {
        return databaseDriver.deleteSharedSpaceByIDs(client, friend);
    }

    /**
     * Gets the card number.
     *
     * @return the card number
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Sets the card number.
     *
     * @param cardNum the new card number
     */
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
