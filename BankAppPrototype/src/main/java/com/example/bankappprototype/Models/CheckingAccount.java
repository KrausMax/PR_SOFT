package com.example.bankappprototype.Models;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account {
    //the number of transactions a client is allowed to do per day
    private final IntegerProperty transactionLimit;

    public CheckingAccount(int owner, String accountNumber, double balance, int tLimit,int id) {
        super(owner, accountNumber, balance, id);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", tLimit);
    }

    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
