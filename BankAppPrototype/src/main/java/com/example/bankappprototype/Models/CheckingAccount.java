package com.example.bankappprototype.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a checking account that extends the Account class.
 * Includes an additional property for transaction limit.
 */
public class CheckingAccount extends Account {
    // The number of transactions a client is allowed to do per day
    private final IntegerProperty transactionLimit;

    /**
     * Constructs a CheckingAccount with the specified owner, account number, balance, transaction limit, and ID.
     *
     * @param owner the owner ID of the account
     * @param accountNumber the account number
     * @param balance the balance of the account
     * @param tLimit the transaction limit for the account
     * @param id the ID of the account
     */
    public CheckingAccount(int owner, String accountNumber, double balance, int tLimit, int id) {
        super(owner, accountNumber, balance, id);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", tLimit);
    }

    /**
     * Gets the transaction limit property of the checking account.
     *
     * @return the transaction limit property
     */
    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }

    /**
     * Returns a string representation of the checking account, which is the account number.
     *
     * @return the account number as a string
     */
    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
