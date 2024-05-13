package com.example.bankappprototype.Models;

import java.sql.*;
import java.time.LocalDate;

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


    /*
    * Utility Section
    * */

    public ResultSet getClientID(String email) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT ID FROM Client WHERE email='"+email+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

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

    public ResultSet getCheckingAccountData(String owner) {
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

    public ResultSet getSavingsAccountData(String owner) {
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

}
