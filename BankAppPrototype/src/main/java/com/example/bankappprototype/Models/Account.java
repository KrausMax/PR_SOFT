package com.example.bankappprototype.Models;

import javafx.beans.property.*;

/**
 * Represents a bank account with an owner, account number, balance, and ID.
 */
public class Account {
    private final IntegerProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;
    private final IntegerProperty id;

    /**
     * Constructs an Account with the specified owner, account number, balance, and ID.
     *
     * @param owner the owner ID of the account
     * @param accountNumber the account number
     * @param balance the balance of the account
     * @param id the ID of the account
     */
    public Account(int owner, String accountNumber, double balance, int id) {
        this.owner = new SimpleIntegerProperty(this, "Owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "Balance", balance);
        this.id = new SimpleIntegerProperty(this, "Id", id);
    }

    /**
     * Gets the owner property of the account.
     *
     * @return the owner property
     */
    public IntegerProperty ownerProperty() {
        return owner;
    }

    /**
     * Gets the account number property of the account.
     *
     * @return the account number property
     */
    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    /**
     * Gets the balance property of the account.
     *
     * @return the balance property
     */
    public DoubleProperty balanceProperty() {
        return balance;
    }

    /**
     * Gets the ID property of the account.
     *
     * @return the ID property
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Returns a string representation of the account, which is the account number.
     *
     * @return the account number as a string
     */
    @Override
    public String toString() {
        return accountNumber.getValue();
    }
}
