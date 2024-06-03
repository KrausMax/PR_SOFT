package com.example.bankappprototype.Models;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String getClientEmailByAccountID(int id) {
        Statement statement;
        ResultSet resultSet = null;
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

    public String verifyCard(String cardNumber, String sequenceNumber, String secretNumber) {
        Statement statement;
        ResultSet resultSet = null;
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
}
