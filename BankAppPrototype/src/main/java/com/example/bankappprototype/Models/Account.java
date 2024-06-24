package com.example.bankappprototype.Models;

import javafx.beans.property.*;

public class Account {
    private final IntegerProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;
    private final IntegerProperty id;

    public Account(int owner, String accountNumber, double balance, int id) {
        this.owner = new SimpleIntegerProperty(this, "Owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "Balance", balance);
        this.id = new SimpleIntegerProperty(this, "Id", id);
    }

    public IntegerProperty ownerProperty() {
        return owner;
    }

    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    @Override
    public String toString() {
        return accountNumber.getValue();
    }
}
