package com.example.bankappprototype.Models;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Handles database operations related to the banking application.
 */
public class DatabaseDriver {
    Connection conn;

    /**
     * Initializes the database connection.
     */
    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves client data based on the provided email address and password.
     *
     * @param pAddress the email address of the client
     * @param password the password of the client
     * @return a ResultSet containing the client data
     */
    public ResultSet getClientData(String pAddress, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Client WHERE email='"+pAddress+"' AND Password='"+password+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves all transactions of a client based on the client ID.
     *
     * @param clientID the ID of the client
     * @return a ResultSet containing all transactions of the client
     */
    public ResultSet getAllTransactionsOfClient(int clientID) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM 'Transaction' WHERE Sender ='"+clientID+"' or receiver ='"+clientID+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves all friends of a client based on the client ID.
     *
     * @param clientID the ID of the client
     * @return a ResultSet containing all friends of the client
     */
    public ResultSet getAllFriendsOfClient(int clientID) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM 'Friends' WHERE Client ='"+clientID+"' or FriendClient ='"+clientID+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves all spaces (accounts) of a client based on the client ID.
     *
     * @param clientID the ID of the client
     * @return a ResultSet containing all spaces of the client
     */
    public ResultSet getAllSpaces(int clientID) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM 'Account' WHERE OWNER ='"+clientID+"' AND MAINACCOUNT = '0';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves all cards associated with the given IBAN.
     *
     * @param iban the IBAN of the account
     * @return a ResultSet containing all cards associated with the IBAN
     */
    public ResultSet getCards(String iban) {
        int account = getAccountId(iban);
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM 'Card' WHERE ACCOUNT ="+account+";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves the card details based on the card number.
     *
     * @param cardNum the card number
     * @return a ResultSet containing the card details
     */
    public ResultSet getCard(String cardNum) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT CardLimit, StatusOnline, StatusTerminal FROM Card WHERE CardNumber = '"+cardNum+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Updates the card details including limit, online status, and terminal status.
     *
     * @param limit the new card limit
     * @param online the new online status
     * @param terminal the new terminal status
     * @param changeOnline whether to change the online status
     * @param changeTerminal whether to change the terminal status
     */
    public void updateCard(int limit, int online, int terminal, boolean changeOnline, boolean changeTerminal) {
        int accountId = -1;
        int prevLimit = -1;
        ResultSet resultSet;
        Statement statement;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select Account, CardLimit from Card where Card.CardNumber='"+Model.getInstance().getCardNum()+"';");
            if (resultSet.isBeforeFirst() ) {
                accountId = resultSet.getInt(1);
                prevLimit = resultSet.getInt(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(limit == -1) {
            limit = prevLimit;
        }

        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE Card  SET CardLimit =" + limit + ", StatusOnline = " + online + ", StatusTerminal = " + terminal + " WHERE CardNumber = '" + Model.getInstance().getCardNum() + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement transStatement;
        if (changeOnline) {
            try {
                transStatement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

                LocalDateTime ldt = LocalDateTime.now();

                transStatement.setInt(1, accountId);
                transStatement.setDouble(2, 0);
                transStatement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
                transStatement.setString(4,"Card :" + Model.getInstance().getCardNum());
                transStatement.setString(5,TransactionTypes.KARTE_ONLINE_STATUS.toString());
                transStatement.setInt(6,1);
                transStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (changeTerminal) {
            try {
                transStatement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

                LocalDateTime ldt = LocalDateTime.now();

                transStatement.setInt(1, accountId);
                transStatement.setDouble(2, 0);
                transStatement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
                transStatement.setString(4,"Card :" + Model.getInstance().getCardNum());
                transStatement.setString(5,TransactionTypes.KARTE_BANKOMAT_STATUS.toString());
                transStatement.setInt(6,1);
                transStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (limit != prevLimit) {
            try {
                transStatement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

                LocalDateTime ldt = LocalDateTime.now();

                transStatement.setInt(1, accountId);
                transStatement.setDouble(2, limit);
                transStatement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
                transStatement.setString(4,"Card :" + Model.getInstance().getCardNum());
                transStatement.setString(5,TransactionTypes.KARTE_LIMIT_AENDERUNG.toString());
                transStatement.setInt(6,1);
                transStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the account ID based on the given IBAN.
     *
     * @param iban the IBAN of the account
     * @return the account ID
     */
    public int getAccountId(String iban) {
        int accountID = -1;
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select ID from Account where AccountNumber ='"+ iban +"';");
            accountID = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountID;
    }

    /**
     * Checks if the given IBAN is valid.
     *
     * @param iban the IBAN to validate
     * @return true if the IBAN is valid, false otherwise
     */
    public boolean ibanValid(String iban) {
        boolean valid = false;
        Statement statement;
        ResultSet resultSet;
        int mainAcc = -1;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select MainAccount from Account where AccountNumber ='"+ iban +"';");
            valid = resultSet.isBeforeFirst();
            if(valid) {
                mainAcc = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (mainAcc != 1) {
            return false;
        }
        return valid;
    }

    /**
     * Gets the account balance based on the given IBAN.
     *
     * @param iban the IBAN of the account
     * @return the account balance
     */
    public double getAccountBalance(String iban) {
        double balance = -1;
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select Balance from Account where AccountNumber ='"+ iban +"';");
            balance = resultSet.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Retrieves admin data based on the provided username and password.
     *
     * @param username the username of the admin
     * @param password the password of the admin
     * @return a ResultSet containing the admin data
     */
    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admin WHERE Username='"+username+"' AND Password='"+password+"';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Creates a new client with the provided details.
     *
     * @param fName the first name of the client
     * @param lName the last name of the client
     * @param email the email address of the client
     * @param password the password of the client
     * @param date the date the client was created
     */
    public void createClient(String fName, String lName, String email, String password, LocalDate date) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Client(FirstName, LastName, email, Password, Date, image)" +
                    "VALUES ('"+fName+"', '"+lName+"','"+email+"','"+password+"', '"+date.toString()+"', 'C:\\Users\\User\\IdeaProjects\\PR_SOFT\\BankAppPrototype\\src\\main\\resources\\Images\\Max_Mustermann.png');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the client details with the provided information.
     *
     * @param fName the first name of the client
     * @param lName the last name of the client
     * @param email the email address of the client
     * @param password the new password of the client
     */
    public void updateClient(String fName, String lName, String email, String password) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE " +
                    "Client SET FirstName = '" + fName + "', LastName = '" + lName + "', Password = '" + password + "'" +
                    "WHERE email = '" + email + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the profile picture of the client.
     *
     * @param filepath the new file path of the profile picture
     * @param email the email address of the client
     */
    public void updateProfilePic(String filepath, String email) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE " +
                    "Client SET image = '" + filepath + "' WHERE email = '" + email + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a main account with the provided details.
     *
     * @param number the account number
     * @param tLimit the transaction limit
     * @param balance the initial balance
     */
    public void createMainAccount(String number, double tLimit, double balance) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Account(Owner, AccountNumber, TransactionLimit, Balance, MainAccount)" +
                    "VALUES ('"+(getLastClientsId()+1)+"','"+number+"','"+tLimit+"','"+balance+"','"+1+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new space (account) with the provided details.
     *
     * @param number the account number
     * @param tLimit the transaction limit
     * @param balance the initial balance
     * @param space_name the name of the space
     * @param space_image the image path of the space
     */
    public void createSpace(String number, double tLimit, double balance, String space_name, String space_image) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Account(Owner, AccountNumber, TransactionLimit, Balance, MainAccount, space_name, space_image)" +
                    "VALUES ('"+Model.getInstance().getClient().idProperty().getValue()+"','"+number+"','"+tLimit+"','"+balance+"','"+0+"','"+space_name+"','"+space_image+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new card with the specified limit.
     *
     * @param limit the card limit
     */
    public void createCard(int limit) {
        String iban = Model.getInstance().getCardIban();
        int account = getAccountId(iban);
        String cardNumber = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int randomNumber = random.nextInt(10000);
            String formattedNumber = String.format("%04d", randomNumber);
            cardNumber += formattedNumber + " ";
        }
        int randomNumber = random.nextInt(10000);
        String formattedNumber = String.format("%04d", randomNumber);
        cardNumber += formattedNumber;

        int pin = 100 + random.nextInt(900);
        int sequenceNum = 10 + random.nextInt(90);

        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Card(Account, CardNumber, SequenceNumber, SecretNumber, CardLimit, StatusOnline, StatusTerminal)" +
                    "VALUES ('"+account+"','"+cardNumber+"','"+sequenceNum+"','"+pin+"','"+limit+"','"+1+"','"+1+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Model.getInstance().getDatabaseDriver().payment(account, 20, "Bestellung der Karte " + cardNumber, TransactionTypes.UEBERWEISUNG.toString(), 1);
    }

    /**
     * Retrieves all client data.
     *
     * @return a ResultSet containing all client data
     */
    public ResultSet getAllClientsData() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT  * FROM  Client;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Gets the last client ID in the database.
     *
     * @return the last client ID
     */
    public int getLastClientsId() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Client';");
            id = resultSet.getInt("seq");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Gets the client's email based on the account ID.
     *
     * @param id the account ID
     * @return the client's email
     */
    public String getClientEmailByAccountID(int id) {
        Statement statement;
        ResultSet resultSet;
        String name = "NameNotFound";
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select email from Client join Account on client.ID = Account.Owner where Account.ID ="+ id +";");
            name = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * Gets the client's email based on the client ID.
     *
     * @param id the client ID
     * @return the client's email
     */
    public String getClientEmailByID(int id) {
        Statement statement;
        ResultSet resultSet;
        String name = "NameNotFound";
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select email from Client where ID ="+ id +";");
            name = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * Gets the client ID based on the email address.
     *
     * @param email the email address
     * @return the client ID
     */
    public int getClientIDByEMail(String email) {
        Statement statement;
        ResultSet resultSet;
        int id = 0;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select ID from Client where email ='"+ email +"';");
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Retrieves the checking account data of a client based on the owner ID.
     *
     * @param owner the owner ID
     * @return a ResultSet containing the checking account data
     */
    public ResultSet getCheckingAccountData(int owner) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Account WHERE Owner='"+owner+"' AND MainAccount=1;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves the savings account data of a client based on the owner ID.
     *
     * @param owner the owner ID
     * @return a ResultSet containing the savings account data
     */
    public ResultSet getSavingsAccountData(int owner) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Account WHERE Owner='"+owner+"' AND MainAccount=0;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves the account data based on the account ID.
     *
     * @param id the account ID
     * @return a ResultSet containing the account data
     */
    public ResultSet getAccountById(int id) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Account WHERE ID='"+id+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Verifies a card based on the provided card number, sequence number, and secret number.
     *
     * @param cardNumber the card number
     * @param sequenceNumber the sequence number
     * @param secretNumber the secret number
     * @return the account number if the card is valid, null otherwise
     */
    public String verifyCard(String cardNumber, String sequenceNumber, String secretNumber) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select Account from Card where Card.CardNumber='"+cardNumber+"' AND Card.SequenceNumber='"+sequenceNumber+"' AND card.SecretNumber='"+secretNumber+"';");
            if (resultSet.isBeforeFirst() ) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Processes a payment transaction between two accounts.
     *
     * @param senderID   The ID of the sender's account
     * @param amount     The amount of money to be transferred
     * @param message    A message associated with the transaction
     * @param type       The type of the transaction
     * @param receiverID The ID of the receiver's account
     * @return True if the transaction was successful, false otherwise
     */
    public boolean payment(int senderID, double amount, String message, String type, int receiverID) {
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

            LocalDateTime ldt = LocalDateTime.now();

            statement.setInt(1, senderID);
            statement.setDouble(2, amount);
            statement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
            statement.setString(4, message);
            statement.setString(5,type);
            statement.setInt(6,receiverID);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        Statement updateStatement;
        try {
            updateStatement = this.conn.createStatement();
            updateStatement.executeUpdate("UPDATE Account  SET Balance = Balance - " + amount + " WHERE ID = " + senderID);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            updateStatement = this.conn.createStatement();
            updateStatement.executeUpdate("UPDATE Account  SET Balance = Balance + " + amount + " WHERE ID = " + receiverID);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Adds a new friend relationship between two clients.
     *
     * @param client the ID of the first client
     * @param friend the ID of the second client (friend)
     * @return true if the friend was added successfully, false otherwise
     */
    public boolean addNewFriend(int client, int friend) {
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement("insert INTO 'Friends' (Client, FriendClient) VALUES  (?, ?)");

            statement.setInt(1, client);
            statement.setInt(2, friend);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Adds a shared space between two friends.
     *
     * @param client the ID of the first client
     * @param friend the ID of the second client (friend)
     * @param spaceId the ID of the shared space
     * @return true if the shared space was added successfully, false otherwise
     */
    public boolean addSharedSpace(int client, int friend, int spaceId) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE " +
                    "Friends SET SharedSpace = '" + spaceId + "' WHERE Client = '" + client + "' AND FriendClient = '" + friend + "';");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Retrieves the space (account) details based on the space ID.
     *
     * @param id the space ID
     * @return a ResultSet containing the space details
     */
    public ResultSet getSpaceByID(int id) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM 'Account' WHERE ID ='"+id+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Retrieves all members of a shared space based on the shared space ID.
     *
     * @param id the shared space ID
     * @return a ResultSet containing all members of the shared space
     */
    public ResultSet getSharedSpaceMembersBySharedSpaceID(int id) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM 'Friends' WHERE SharedSpace ='"+id+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Deletes a friendship between two clients based on their IDs.
     *
     * @param client the ID of the first client
     * @param friend the ID of the second client (friend)
     * @return true if the friendship was deleted successfully, false otherwise
     */
    public boolean deleteFriendsByIDs(int client, int friend) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM Friends WHERE Client = " + client + " AND FriendClient = "+ friend +";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes a shared space between two clients based on their IDs.
     *
     * @param client the ID of the first client
     * @param friend the ID of the second client (friend)
     * @return true if the shared space was deleted successfully, false otherwise
     */
    public boolean deleteSharedSpaceByIDs(int client, int friend) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE " +
                    "Friends SET SharedSpace = NULL WHERE Client = '" + client + "' AND FriendClient = '" + friend + "';");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
