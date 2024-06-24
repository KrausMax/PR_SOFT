package com.example.bankappprototype.Models;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Client Section
    * */

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

    public void createCard(int accountID, String cardNumber, int sequenceNumber, int secretNumber, int cardLimit, int statusOnline, int statusTerminal) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO Card (Account, CardNumber, SequenceNumber, SecretNumber, CardLimit, StatusOnline, StatusTerminal) " +
                    "VALUES (" + accountID + ", '" + cardNumber + "', " + sequenceNumber + ", " + secretNumber + ", " + cardLimit + ", " + statusOnline + ", " + statusTerminal + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        return  resultSet;
    }

    public ResultSet getCard(String cardNum) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT CardLimit, StatusOnline, StatusTerminal FROM Card WHERE CardNumber = '"+cardNum+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  resultSet;
    }

    public void updateCard(int limit, int online, int terminal) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE Card  SET CardLimit =" + limit + ", StatusOnline = " + online + ", StatusTerminal = " + terminal + " WHERE CardNumber = '" + Model.getInstance().getCardNum() + "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    /*
    * Admin Section
    * */

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

    public void createClient(String fName, String lName, String email, String password, LocalDate date) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Client(FirstName, LastName, email, Password, Date)" +
                    "VALUES ('"+fName+"', '"+lName+"','"+email+"','"+password+"', '"+date.toString()+"');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void createSpace(String number, double tLimit, double balance, String space_name, String space_image) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Account(Owner, AccountNumber, TransactionLimit, Balance, MainAccount, space_name, space_image)" +
                    "VALUES ('"+(getLastClientsId()+1)+"','"+number+"','"+tLimit+"','"+balance+"','"+0+"','"+space_name+"','"+space_image+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE Account  SET Balance = Balance - 20 WHERE ID = " + account);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    /*
    * Utility Section
    * */

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

    public boolean addToCardPayment(String accountID, String amount) {
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

            LocalDateTime ldt = LocalDateTime.now();

            statement.setInt(1, 1);
            statement.setFloat(2, Float.parseFloat(amount));
            statement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
            statement.setString(4,"Einzahlung");
            statement.setString(5,TransactionTypes.BANKOMAT_EINZAHLUNG.toString());
            statement.setInt(6,Integer.parseInt(accountID));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean subtractFromCardPayment(String accountID, String amount) {
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

            LocalDateTime ldt = LocalDateTime.now();

            statement.setInt(1, Integer.parseInt(accountID));
            statement.setFloat(2, Float.parseFloat(amount));
            statement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
            statement.setString(4,"Abhebung");
            statement.setString(5,TransactionTypes.BANKOMAT_BEHEBUNG.toString());
            statement.setInt(6,1);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean payWithCard(String accountID, String amount, String message) {
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement("insert INTO 'Transaction' (Sender, Amount, date, message, transaction_type,Receiver) VALUES  (?, ?, ?, ?, ?, ?)");

            LocalDateTime ldt = LocalDateTime.now();

            statement.setInt(1, Integer.parseInt(accountID));
            statement.setFloat(2, Float.parseFloat(amount));
            statement.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt));
            statement.setString(4, message);
            statement.setString(5,TransactionTypes.KARTENZAHLUNG.toString());
            statement.setInt(6,2);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

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
